package cn.mina.boot.web.auth;

import cn.mina.boot.web.auth.cache.CacheTokenConfiguration;
import cn.mina.boot.web.auth.jwt.JwtTokenConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Created by haoteng on 2022/7/21.
 */
@Configuration
@EnableConfigurationProperties({MinaTokenProperties.class})
@ConditionalOnProperty(prefix = "mina.web.auth", name = "enable", havingValue = "true")
@Import({JwtTokenConfiguration.class, CacheTokenConfiguration.class})
public class MinaWebTokenAutoConfiguration {

    @Autowired
    private MinaTokenProperties minaTokenProperties;

//    @Bean
//    @Qualifier
//    public WebMvcConfigurer webMvcConfigurer(AbstractTokenInterceptor tokenInterceptor) {
//        return new WebConfigurer(tokenInterceptor);
//    }
//
//    static class WebConfigurer implements WebMvcConfigurer {
//
//        private AbstractTokenInterceptor tokenInterceptor;
//
//        public WebConfigurer(AbstractTokenInterceptor tokenInterceptor) {
//            this.tokenInterceptor = tokenInterceptor;
//        }
//
//        @Override
//        public void addInterceptors(InterceptorRegistry registry) {
//            // 拦截所有请求，通过判断是否有 @Login注解 决定是否需要登录
//            registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
//        }
//
//    }
}
