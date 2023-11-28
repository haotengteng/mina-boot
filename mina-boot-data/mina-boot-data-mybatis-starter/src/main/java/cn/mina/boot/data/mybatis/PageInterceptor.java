package cn.mina.boot.data.mybatis;

import cn.mina.boot.common.exception.MinaBaseException;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * mybatis分页拦截器
 *
 * @author Created by haoteng on 2022/8/4.
 */
@Intercepts({@Signature(type = Executor.class, method = "query", args = {
        MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "prepare", args = {
                Connection.class, Integer.class})
})
public class PageInterceptor implements Interceptor {

    protected static final ThreadLocal<Long> countStorage = new ThreadLocal<>();
    //每页显示的条目数
    private int pageSize;
    //当前现实的页数
    private int page;

    public static long getRowCount(final String sql, final BoundSql boundSql,
                                   final MappedStatement mappedStatement, final Object parameterObject) throws SQLException {
        final String countSql = "select count(1) from (" + sql + ")temp_count";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            if (conn == null) {
                conn = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
            }
            ps = conn.prepareStatement(countSql);
            BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),
                    countSql,
                    boundSql.getParameterMappings(),
                    parameterObject);

            Arrays.asList("additionalParameters", "metaParameters").forEach(s -> {
                Field field = ReflectionUtils.findField(BoundSql.class, s);
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, countBS, ReflectionUtils.getField(field, boundSql));
            });

            setParameters(ps, mappedStatement, countBS, parameterObject);
            rs = ps.executeQuery();
            long count = 0;
            if (rs.next()) {
                count = rs.getLong(1);
            }
            return count;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
     *
     * @param ps              表示预编译的 SQL 语句的对象。
     * @param mappedStatement MappedStatement
     * @param boundSql        SQL
     * @param parameterObject 参数对象
     * @throws java.sql.SQLException 数据库异常
     */
    public static void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            MetaObject metaObject = parameterObject == null ? null :
                    configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    PropertyTokenizer prop = new PropertyTokenizer(propertyName);
                    if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
                        value = boundSql.getAdditionalParameter(prop.getName());
                        if (value != null) {
                            value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
                        }
                    } else {
                        value = metaObject == null ? null : metaObject.getValue(propertyName);
                    }
                    @SuppressWarnings("rawtypes")
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    if (typeHandler == null) {
                        throw new ExecutorException("There was no TypeHandler found for parameter " + propertyName + " of statement " + mappedStatement.getId());
                    }
                    typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
                }
            }
        }
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 分页
        doPageHandler(invocation);
        // 获取总数量
        getCount(invocation);
        //调用原对象的方法，进入责任链的下一级
        return invocation.proceed();
    }

    private void getCount(Invocation invocation) throws SQLException {
        if (invocation.getTarget() instanceof Executor) {
            final MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            String mapId = mappedStatement.getId();
            //拦截以.ByPage结尾的请求，分页功能的统一实现
            if (mapId.matches(".+ByPage$")) {
                //拦截需要分页的SQL
                Object parameter = invocation.getArgs()[1];
                BoundSql boundSql = mappedStatement.getBoundSql(parameter);
                Object parameterObject = boundSql.getParameterObject();
                String originSql = boundSql.getSql().trim();
                long rowCount = getRowCount(originSql, boundSql, mappedStatement, parameterObject);
                countStorage.set(rowCount);
            }
        }
    }

    private void doPageHandler(Invocation invocation) {
        //获取StatementHandler，默认是RoutingStatementHandler
        if (invocation.getTarget() instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            //获取statementHandler包装类
            MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

            //分离代理对象链
            while (metaObject.hasGetter("h")) {
                Object obj = metaObject.getValue("h");
                metaObject = SystemMetaObject.forObject(obj);
            }

            while (metaObject.hasGetter("target")) {
                Object obj = metaObject.getValue("target");
                metaObject = SystemMetaObject.forObject(obj);
            }

            //获取查询接口映射的相关信息
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
            String mapId = mappedStatement.getId();
            //拦截以.ByPage结尾的请求，分页功能的统一实现
            if (mapId.matches(".+ByPage$")) {
                //获取进行数据库操作时管理参数的handler
                ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
                //获取请求时的参数
                if (parameterHandler.getParameterObject() instanceof MapperMethod.ParamMap) {
                    Map<String, Object> paraObject = (Map<String, Object>) parameterHandler.getParameterObject();
                    //参数名称和在dao中@Param设置的名称一致
                    page = (int) paraObject.get("page");
                    int size = (int) paraObject.get("pageSize");
                    if (size != 0) {
                        pageSize = size;
                    }
                } else {
                    // 通过反射获取分页参数
                    Object param = parameterHandler.getParameterObject();
                    Class<?> paramClass = param.getClass();
                    try {
                        Field[] fields = paramClass.getDeclaredFields();
                        Field pageField;
                        if (Arrays.stream(fields).anyMatch(field -> "page".equals(field.getName()))) {
                            pageField = paramClass.getDeclaredField("page");
                        } else {
                            // 子类不存在 page字段，尝试父类获取
                            pageField = paramClass.getSuperclass().getDeclaredField("page");
                        }
                        pageField.setAccessible(true);
                        page = (int) pageField.get(param);

                        Field pageSizeField;
                        if (Arrays.stream(fields).anyMatch(field -> "pageSize".equals(field.getName()))) {
                            pageSizeField = paramClass.getDeclaredField("pageSize");
                        } else {
                            pageSizeField = paramClass.getSuperclass().getDeclaredField("pageSize");
                        }
                        pageSizeField.setAccessible(true);
                        // 未设置分页数量的情况下，使用默认配置
                        int size = (int) pageSizeField.get(param);
                        if (size != 0) {
                            pageSize = size;
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchFieldException e) {
                        throw new MinaBaseException("jungle data mybatis page params error");
                    }
                }
                String sql = (String) metaObject.getValue("delegate.boundSql.sql");
                //也可以通过statementHandler直接获取
                //sql = statementHandler.getBoundSql().getSql();

                //构建分页功能的sql语句
                String limitSql;
                sql = sql.trim();
                limitSql = sql + " limit " + (page - 1) * pageSize + "," + pageSize;

                //将构建完成的分页sql语句赋值个体'delegate.boundSql.sql'
                metaObject.setValue("delegate.boundSql.sql", limitSql);
            }
        }
    }

    //获取代理对象
    @Override
    public Object plugin(Object o) {
        //生成object对象的动态代理对象
        return Plugin.wrap(o, this);
    }

    //设置代理对象的参数
    @Override
    public void setProperties(Properties properties) {
        //如果项目中分页的pageSize是统一的，也可以在这里统一配置和获取，这样就不用每次请求都传递pageSize参数了。参数是在配置拦截器时配置的。
        this.pageSize = Integer.parseInt(properties.getProperty("pageSize", "10"));
    }
}
