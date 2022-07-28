package cn.mina.web.auth.cache;

import cn.mina.web.auth.MinaTokenProperties;
import cn.mina.web.auth.MinaWebAuthTools;
import cn.mina.web.auth.TokenGenerator;
import cn.mina.web.auth.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Created by haoteng on 2022/7/28.
 */
@Configuration
@ConditionalOnExpression("'${mina.web.auth.type}'.equals('cache')")
@ConditionalOnClass(name = "cn.mina.cache.redis.MinaCacheRedisAutoConfiguration")
public class CacheTokenConfiguration {

    @Autowired
    private MinaTokenProperties properties;


    @Bean
    @ConditionalOnClass(name = "cn.mina.cache.redis.MinaCacheRedisAutoConfiguration")
    public CacheTokenInterceptor cacheTokenInterceptor() {
        return new CacheTokenInterceptor(properties);
    }

    @PostConstruct
    public void init() {
        // TODO: 2022/7/28 实现缓存token生成逻辑 
        MinaWebAuthTools.token.generator = t -> JwtTokenUtil.encode(t, Integer.valueOf(properties.getExpireTime()), properties.getSecret());
    }
}
