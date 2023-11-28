package cn.mina.boot.data.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * @author Created by haoteng on 2023/8/22.
 */
@Slf4j
@Intercepts({
        @Signature(
                type = StatementHandler.class,
                method = "prepare",
                args = {Connection.class, Integer.class}
        )
})
public class SqlLogInterceptor implements Interceptor {

    /**
     * 日志拦截器启用标记
     */
    private String enable;

    private static MappedStatement getMappedStatement(StatementHandler statementHandler) throws NoSuchFieldException, IllegalAccessException {
        Field delegateField = statementHandler.getClass().getDeclaredField("delegate");
        delegateField.setAccessible(true);
        Object delegate = delegateField.get(statementHandler);

        Field mappedStatementField = ReflectionUtils.findField(delegate.getClass(), "mappedStatement");
        mappedStatementField.setAccessible(true);
        MappedStatement mappedStatement = (MappedStatement) ReflectionUtils.getField(mappedStatementField, delegate);
        return mappedStatement;
    }

    private static void showSql(Configuration configuration, BoundSql boundSql, long time, String sqlId) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        //替换空格、换行、tab缩进等
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        logs(time, sql, sqlId);
    }

    private static String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value.replace("$", "\\$");
    }

    private static void logs(long time, String sql, String sqlId) {
        String sb = " ==>  Method: " + sqlId + ", Time cost:  " + time + "ms";
        log.info(sb);
        log.info(" ==>  Executed Sql: " + sql + ";");
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if ("true".equals(enable)) {
            long start = System.currentTimeMillis();
            Object proceed = invocation.proceed();
            try {
                StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
                BoundSql boundSql = statementHandler.getBoundSql();
                // 获取 MappedStatement
                MappedStatement mappedStatement = getMappedStatement(statementHandler);
                String sqlId = mappedStatement.getId();
                Configuration configuration = mappedStatement.getConfiguration();
                long time = System.currentTimeMillis() - start;
                showSql(configuration, boundSql, time, sqlId);
            } catch (Exception e) {
                log.warn("SqlLogInterceptor 拦截器打印sql日志异常:", e);
            }
            return proceed;
        } else {
            //调用原对象的方法，进入责任链的下一级
            return invocation.proceed();
        }
    }

    private String formatSql(String sql, Object parameterObject) {
        if (parameterObject != null) {
            MetaObject metaObject = SystemMetaObject.forObject(parameterObject);
            if (metaObject != null) {
                String[] parameterNames = metaObject.getGetterNames();
                for (String paramName : parameterNames) {
                    Object value = metaObject.getValue(paramName);
                    String parameterString = value != null ? value.toString() : "null";
                    sql = sql.replaceFirst("\\?", parameterString);
                }
            }
        }
        return sql;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        enable = properties.getProperty("log.enable", "false");
    }
}
