package cn.mina.boot.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.ObjectOutput;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * json 工具类
 * 1.序列化类上，使用 @JsonInclude(JsonInclude.Include.NON_NULL) 序列化忽略所有null字段
 * 2.时间字段上，使用 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") 保持格式统一
 * 3.序列化类上，必须提供无参构造方法！！
 *
 * @author Created by haoteng on 2022/7/27.
 */
public class JsonUtil {
    private static ObjectMapper MAPPER = new ObjectMapper();

    // 日起格式化
    private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {
        //对象的所有字段全部列入
        MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //取消默认转换timestamps形式
        MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        //忽略空Bean转json的错误
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        MAPPER.setDateFormat(new SimpleDateFormat(STANDARD_FORMAT));
        //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 设置时区
        MAPPER.setTimeZone(TimeZone.getTimeZone("GMT+8"));
    }

    public static ObjectMapper mapper() {
        return MAPPER;
    }

    public static void setMapper(ObjectMapper mapper) {
        JsonUtil.MAPPER = mapper;
    }

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
        return mapper().writeValueAsString(obj);
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
    public static <T> T toBean(String jsonStr, Class<T> clazz) {
        if (StringUtils.isBlank(jsonStr) || clazz == null) {
            return null;
        }
        return mapper().readValue(jsonStr, clazz);
    }


    /**
     * 解析对象
     * 反序列化为Object
     *
     * @param reference reference
     * @param jsonStr   json str
     * @return {@link T}
     */
    @SneakyThrows
    public static <T> T toBean(String jsonStr, TypeReference<T> reference) {
        if (StringUtils.isBlank(jsonStr) || reference == null) {
            return null;
        }
        return mapper().readValue(jsonStr, reference);
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
    public static <T> List<T> toList(String listJsonStr, Class<T> clazz) {
        if (StringUtils.isBlank(listJsonStr) || clazz == null) {
            return Collections.emptyList();
        }
        return mapper().readValue(listJsonStr, List.class);
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
    public static <K, V> Map<K, V> toMap(String mapJsonStr, Class<K> kClazz, Class<V> vClazz) {
        if (StringUtils.isBlank(mapJsonStr) || kClazz == null || vClazz == null) {
            return Collections.emptyMap();
        }
        return mapper().readValue(mapJsonStr, mapper().getTypeFactory().constructParametricType(Map.class, kClazz, vClazz));
    }


    /**
     * 获取jsonNode对象
     *
     * @return {@link ObjectNode}
     */
    public static ObjectNode getObjectNode() {
        return mapper().createObjectNode();
    }

    /*
     * jsonstr 转换成转换成JsonNode
     */
    @SneakyThrows
    public static JsonNode toJsonNode(String jsonString) {
        if (isEmpty(jsonString)) {
            return null;
        }
        return mapper().readTree(jsonString);
    }

    /*
     * jsonNode 转换成转换成bean
     */
    @SneakyThrows
    public static <T> T toBean(JsonNode jsonNode, Class<T> t) {
        if (jsonNode == null) {
            return null;
        }
        return (T) mapper().convertValue(jsonNode, t);
    }

    /**
     * @param jsonNode
     * @param t
     * @param <T>
     * @return
     */
    @SneakyThrows
    public static <T> JsonNode toJsonNode(T t, JsonNode jsonNode) {
        if (t == null) {
            return null;
        }
        return mapper().convertValue(t, JsonNode.class);
    }

    @SneakyThrows
    public static ObjectNode create() {
        return mapper().createObjectNode();
    }

}
