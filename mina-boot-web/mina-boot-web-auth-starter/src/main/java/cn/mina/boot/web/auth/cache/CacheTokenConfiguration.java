package cn.mina.boot.web.auth.cache;

import cn.mina.boot.common.util.DateUtils;
import cn.mina.boot.common.util.JsonUtils;
import cn.mina.boot.common.util.MD5Utils;
import cn.mina.boot.web.auth.MinaTokenProperties;
import cn.mina.boot.web.auth.MinaWebAuthTools;
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
@ConditionalOnClass(name = "cn.mina.boot.cache.redis.MinaCacheRedisAutoConfiguration")
public class CacheTokenConfiguration {

    @Autowired
    private MinaTokenProperties properties;


    @Bean
    @ConditionalOnClass(name = "cn.mina.boot.cache.redis.MinaCacheRedisAutoConfiguration")
    public CacheTokenInterceptor cacheTokenInterceptor() {
        return new CacheTokenInterceptor(properties);
    }

    @PostConstruct
    public void init() {
        MinaWebAuthTools.token.generator = t -> MD5Utils.MD5Lower(JsonUtils.toJSONString(t), properties.getSecret() + DateUtils.nowMs());
    }
}
