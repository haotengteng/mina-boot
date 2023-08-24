package cn.mina.boot.web.common.interceptor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Created by haoteng on 2022/7/21.
 */
@Configuration
@ConditionalOnWebApplication
public class MinaWebInterceptorAutoConfiguration {

    @Bean
    @Qualifier
    public WebMvcConfigurer webMvcConfigurer(List<MinaHandlerInterceptor> handlerInterceptors) {
        return new WebConfigurer(handlerInterceptors);
    }

    static class WebConfigurer implements WebMvcConfigurer {

        private List<MinaHandlerInterceptor> handlerInterceptors;

        public WebConfigurer(List<MinaHandlerInterceptor> handlerInterceptors) {
            this.handlerInterceptors = handlerInterceptors;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {

            if (handlerInterceptors != null) {
                handlerInterceptors.forEach(handlerInterceptor -> {
                    // 拦截所有请求，通过判断是否有 @Login注解 决定是否需要登录
                    registry.addInterceptor(handlerInterceptor).addPathPatterns(handlerInterceptor.havePathPattern());
                });
            }
        }

    }
}
