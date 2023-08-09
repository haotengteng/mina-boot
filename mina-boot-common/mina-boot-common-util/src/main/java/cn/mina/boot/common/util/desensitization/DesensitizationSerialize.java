package cn.mina.boot.common.util.desensitization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Created by haoteng on 2023/8/9.
 */
public class DesensitizationSerialize extends JsonSerializer<String> implements ContextualSerializer {
    private DesensitizationEnum type;


    /**
     * 序列化
     *
     * @param s
     * @param jsonGenerator
     * @param serializerProvider
     * @throws IOException
     */
    @Override
    public void serialize(String s, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(type.getFunction().apply(s));
    }

    /**
     * 在序列化时获取字段注解属性
     *
     * @param serializerProvider
     * @param beanProperty
     * @return
     * @throws JsonMappingException
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider,
                                              BeanProperty beanProperty) throws JsonMappingException {
        // 主要判断字符串，不是字符串的话就跳过
        if (Objects.nonNull(beanProperty) && Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
            Desensitization desensitization = beanProperty.getAnnotation(Desensitization.class);
            if (!ObjectUtils.isEmpty(desensitization)) {
                // 如果属性上有Desensitization注解，就获取枚举类型
                this.type = desensitization.type();
                return this;
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(beanProperty);
    }

}
