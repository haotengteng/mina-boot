package cn.mina.boot.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * json 工具类
 * 1.序列化类上，使用 @JsonInclude(JsonInclude.Include.NON_NULL) 序列化忽略所有null字段
 * 2.时间字段上，使用 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 保持格式统一
 * 3.序列化类上，必须提供无参构造方法！！
 *
 * @author Created by haoteng on 2022/7/27.
 */
public class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();


    /**
     * 序列化为JSON字符串
     *
     * @param obj obj
     * @return {@link String}
     */
    @SneakyThrows
    public static String toJSONString(Object obj) {
        if (obj == null) {
            return null;
        }
        return mapper.writeValueAsString(obj);
    }


    /**
     * 解析对象
     * 反序列化为Object
     *
     * @param clazz   clazz
     * @param jsonStr json str
     * @return {@link T}
     */
    @SneakyThrows
    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        if (StringUtils.isBlank(jsonStr) || clazz == null) {
            return null;
        }
        return mapper.readValue(jsonStr, clazz);
    }

    /**
     * 获取jsonNode对象
     *
     * @return {@link ObjectNode}
     */
    public static ObjectNode getObjectNode() {
        return mapper.createObjectNode();
    }


    /**
     * 解析List
     * 反序列化为List集合
     *
     * @param clazz       clazz
     * @param listJsonStr 列表json str
     * @return {@link List}<{@link T}>
     */
    @SneakyThrows
    public static <T> List<T> parseList(String listJsonStr, Class<T> clazz) {
        if (StringUtils.isBlank(listJsonStr) || clazz == null) {
            return Collections.emptyList();
        }
        return mapper.readValue(listJsonStr, List.class);
    }


    /**
     * 解析Map
     * 反序列化为Map集合
     *
     * @param mapJsonStr 地图json str
     * @param kClazz     k clazz
     * @param vClazz     v clazz
     * @return {@link Map}<{@link K}, {@link V}>
     */
    @SneakyThrows
    public static <K, V> Map<K, V> parseMap(String mapJsonStr, Class<K> kClazz, Class<V> vClazz) {
        if (StringUtils.isBlank(mapJsonStr) || kClazz == null || vClazz == null) {
            return Collections.emptyMap();
        }
        return mapper.readValue(mapJsonStr, mapper.getTypeFactory().constructParametricType(Map.class, kClazz, vClazz));
    }


}
