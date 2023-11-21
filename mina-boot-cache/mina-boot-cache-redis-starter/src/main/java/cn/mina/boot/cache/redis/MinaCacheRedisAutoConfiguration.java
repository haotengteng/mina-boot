package cn.mina.boot.cache.redis;

import cn.mina.boot.common.exception.MinaBaseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author Created by haoteng on 2022/7/22.
 */
@Configuration
@EnableConfigurationProperties({MinaCacheRedisProperties.class})
@EnableCaching
@ConditionalOnProperty(prefix = "mina.cache.redis", name = "enable", havingValue = "true", matchIfMissing = true)
public class MinaCacheRedisAutoConfiguration {

    @Autowired
    private MinaCacheRedisProperties properties;

    /**
     * 默认使用jackson序列化对象
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "mina.cache.redis", name = "serializer", havingValue = "jackson", matchIfMissing = true)
    public RedisTemplate<String, String> redisTemplateJackson(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringRedisSerializer serializer = new StringRedisSerializer();
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();

        initMinaRedisUtil(template);
        return template;
    }

    @Bean
    @ConditionalOnProperty(prefix = "mina.cache.redis", name = "serializer", havingValue = "fastjson")
    public RedisTemplate<String, String> redisTemplateFastJson(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        StringRedisSerializer serializer = new StringRedisSerializer();
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(serializer);
        template.setHashValueSerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();

        initMinaRedisUtil(template);
        return template;
    }

//    @Bean
//    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
//        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
//        stringRedisTemplate.setConnectionFactory(factory);
//        return stringRedisTemplate;
//    }

    @Bean
    @ConditionalOnProperty(prefix = "mina.cache.redis", name = "enableTtl", havingValue = "true")
    public RedisCacheConfiguration cacheConfiguration() {
        String duration = properties.getDuration();
        if (properties.getEnableTtl()) {
            if (StringUtils.isBlank(duration)) {
                throw new MinaBaseException("mina cache redis config 'duration' must not be null ");
            }
            // 设置缓存过期时间为 duration 秒后
            return RedisCacheConfiguration.defaultCacheConfig()
                    .entryTtl(Duration.ofSeconds(Integer.parseInt(duration)))
                    .disableCachingNullValues();
        } else {
            return RedisCacheConfiguration.defaultCacheConfig()
                    .disableCachingNullValues();
        }
    }


    private void initMinaRedisUtil(RedisTemplate<String, String> redisTemplate) {
        MinaCacheRedisUtil.redisTemplate = redisTemplate;
    }
}
