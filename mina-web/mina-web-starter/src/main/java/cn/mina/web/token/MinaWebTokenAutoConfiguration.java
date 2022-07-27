package cn.mina.web.token;

import cn.mina.web.context.MinaWebTools;
import cn.mina.web.token.cache.CacheTokenInterceptor;
import cn.mina.web.token.jwt.JwtTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

/**
 * @author Created by haoteng on 2022/7/21.
 */
@Configuration
@EnableConfigurationProperties({MinaTokenProperties.class})
@ConditionalOnProperty(prefix = "mina.web.token", name = "enable", havingValue = "true")
public class MinaWebTokenAutoConfiguration {

    @Autowired
    private MinaTokenProperties minaTokenProperties;

    @PostConstruct
    public void initMinaTools() {
        MinaWebTools.properties.token = minaTokenProperties;
    }

    @Bean
    @ConditionalOnExpression("'${mina.web.token.type}'.equals('jwt')")
    public JwtTokenInterceptor jwtTokenInterceptor() {
        return new JwtTokenInterceptor(minaTokenProperties);
    }

    @Bean
    @ConditionalOnExpression("'${mina.web.token.type}'.equals('cache')")
    @ConditionalOnClass(name = "cn.mina.cache.redis.MinaCacheRedisAutoConfiguration")
    public CacheTokenInterceptor cacheTokenInterceptor() {
        return new CacheTokenInterceptor(minaTokenProperties);
    }


    @Bean
    @Qualifier
    public WebMvcConfigurer webMvcConfigurer(AbstractTokenInterceptor tokenInterceptor) {
        return new WebConfigurer(tokenInterceptor);
    }

    static class WebConfigurer implements WebMvcConfigurer {

        private AbstractTokenInterceptor tokenInterceptor;

        public WebConfigurer(AbstractTokenInterceptor tokenInterceptor) {
            this.tokenInterceptor = tokenInterceptor;
        }

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            // 拦截所有请求，通过判断是否有 @Login注解 决定是否需要登录
            registry.addInterceptor(tokenInterceptor).addPathPatterns("/**");
        }

    }
}
