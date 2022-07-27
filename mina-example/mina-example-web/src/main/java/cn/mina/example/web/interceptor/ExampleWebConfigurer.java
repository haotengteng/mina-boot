package cn.mina.example.web.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Created by haoteng on 2021/6/22.
 */
@Configuration
public class ExampleWebConfigurer implements WebMvcConfigurer {
    @Autowired
    private ExampleTokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求，通过判断是否有 @Login注解 决定是否需要登录
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
    }

}
