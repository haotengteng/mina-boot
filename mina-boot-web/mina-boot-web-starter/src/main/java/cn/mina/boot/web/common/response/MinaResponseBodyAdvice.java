package cn.mina.boot.web.common.response;

import cn.mina.boot.common.util.JsonUtils;
import cn.mina.boot.web.common.context.MinaWebResult;
import cn.mina.boot.web.common.context.MinaWebTools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Created by haoteng on 2022/10/19.
 */
@ControllerAdvice
public class MinaResponseBodyAdvice implements ResponseBodyAdvice {

    @Value("${mina.boot.web.response.advice:true}")
    private Boolean enable;

    /**
     * 返回方法已经是MinaWebResult类型不拦截
     * swagger相关的请求不拦截
     *
     * @param returnType    the return type
     * @param converterType the selected converter type
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        // 判断响应增强是否开启
        if (!enable) {
            return false;
        }
        if (returnType.getNestedParameterType().getName().equals(MinaWebResult.class.getName())) {
            return false;
        } else {
            return !returnType.getDeclaringClass().getName().contains("springfox")
                    && !returnType.getDeclaringClass().getName().contains("actuator");
        }
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接包装，所以要进行些特别的处理
        if (returnType.getGenericParameterType().equals(String.class)) {
            // 将数据包装在MinaWebResult里后，再转换为json字符串响应给前端
            return JsonUtils.toJSONString(MinaWebTools.response.success(body));
            // mvc重定向到/error端点的请求 不进行包装
        } else if ("/error".equals(((ServletServerHttpRequest) request).getServletRequest().getServletPath())) {
            return body;
        }
        // 将原本的数据包装在MinaWebResult里
        return MinaWebTools.response.success(body);
    }
}
