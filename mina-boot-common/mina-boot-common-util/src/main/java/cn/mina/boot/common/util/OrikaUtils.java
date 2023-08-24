package cn.mina.boot.common.util;

import lombok.SneakyThrows;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;
import ma.glasnost.orika.metadata.TypeFactory;

import java.util.Date;
import java.util.List;

/**
 * @author Created by haoteng on 2023/8/10.
 */
public class OrikaUtils {

    /**
     * 默认字段实例
     */
    private static MapperFacade MAPPER_FACADE;


    static {
        // mapNulls 表示 原对象中的null不会拷贝到目标对象
        MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().mapNulls(false).build();
        MAPPER_FACTORY.getConverterFactory().registerConverter(new DateToString());
        MAPPER_FACADE = MAPPER_FACTORY.getMapperFacade();
    }

    public static MapperFacade getMapperFacade() {
        return MAPPER_FACADE;
    }

    public static void setMapperFacade(MapperFacade mapperFacade) {
        MAPPER_FACADE = mapperFacade;
    }

    /**
     * 映射实体（默认字段）
     * 这种映射就是DTO字段名称和实体对象PO之间字段名称一致
     *
     * @param source  数据（对象）DO对象
     * @param toClass 映射类对象 DTO对象
     * @return 映射类对象
     */
    public static <S, D> D map(S source, Class<D> toClass) {
        return MAPPER_FACADE.map(source, toClass);
    }

    /**
     * 将源对象的值拷贝到目标对象中，源对象中的null属性不拷贝到目标对象。
     *
     * @param source 源对象
     * @param target 目标对象
     * @param <S>    源对象类型
     * @param <D>    目标对象类型
     */
    public static <S, D> void map(S source, D target) {
        MAPPER_FACADE.map(source, target);
    }

    /**
     * 映射集合（默认字段）
     * 映射为集合的形式
     *
     * @param targetClass 映射类对象 DTO对象
     * @param sources     数据（集合） DO对象
     * @return 映射类对象
     */
    public static <S, D> List<D> mapAsList(Iterable<S> sources, Class<D> targetClass) {
        return MAPPER_FACADE.mapAsList(sources, targetClass);
    }


    /**
     * 简单复制出新对象列表到数组
     * 通过source.getComponentType() 获得源Class
     * destinationType
     *
     * @param <S>         源对象类型
     * @param <D>         目标对象类型
     * @param target      目标对象数组
     * @param source      源对象数组
     * @param targetClass 目标类型
     * @return 目标对象对象数组
     */
    public static <S, D> D[] convertArray(final S[] source, final D[] target, final Class<D> targetClass) {
        return MAPPER_FACADE.mapAsArray(target, source, targetClass);
    }

    /**
     * 预先获取orika转换所需要的Type，避免每次转换.
     *
     * @param <S>     对象类型
     * @param rawType 要转换的类型
     * @return 转换后的类型
     */
    public static <S> Type<S> getType(final Class<S> rawType) {
        return TypeFactory.valueOf(rawType);
    }


    /**
     * Date 转为 yyyy-MM-dd HH:mm:ss 时间格式
     */
    static class DateToString extends BidirectionalConverter<Date, String> {

        @Override
        public String convertTo(Date date, Type<String> type, MappingContext mappingContext) {
            return DateUtils.dateToStr(date, "yyyy-MM-dd HH:mm:ss");
        }

        @SneakyThrows
        @Override
        public Date convertFrom(String s, Type<Date> type, MappingContext mappingContext) {
            return DateUtils.strToDate(s, "yyyy-MM-dd HH:mm:ss");
        }
    }
}
