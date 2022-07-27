package cn.mina.cache.redis;

import cn.mina.cache.redis.serializer.FastJson2RedisSerializer;
import cn.mina.common.exception.GlobalErrorCode;
import cn.mina.common.exception.MinaGlobalException;
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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
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
    public RedisTemplate<String, Object> redisTemplateJackson(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();

        initMinaRedisUtil(template);
        return template;
    }

    @Bean
    @ConditionalOnProperty(prefix = "mina.cache.redis", name = "serializer", havingValue = "fastjson")
    public RedisTemplate<String, Object> redisTemplateFastJson(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        FastJson2RedisSerializer serializer = new FastJson2RedisSerializer(Object.class);
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();

        initMinaRedisUtil(template);
        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory factory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(factory);
        return stringRedisTemplate;
    }

    @Bean
    @ConditionalOnProperty(prefix = "mina.cache.redis", name = "enableTtl", havingValue = "true")
    public RedisCacheConfiguration cacheConfiguration() {
        String duration = properties.getDuration();
        if (StringUtils.isBlank(duration)) {
            throw new MinaGlobalException(GlobalErrorCode.ERROR_ILLEGAL_CONFIG);
        }
        // 设置缓存过期时间为 duration 秒后
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(Integer.parseInt(duration)))
                .disableCachingNullValues();
    }


    private void initMinaRedisUtil(RedisTemplate<String, Object> redisTemplate) {
        MinaCacheRedisUtil.redisTemplate = redisTemplate;
    }
}
