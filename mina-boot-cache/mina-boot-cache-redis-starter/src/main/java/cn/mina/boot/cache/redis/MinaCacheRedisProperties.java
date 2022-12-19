package cn.mina.boot.cache.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Created by haoteng on 2022/7/22.
 */
@ConfigurationProperties(prefix = "mina.cache.redis")
public class MinaCacheRedisProperties {

    private Boolean enable = true;

    // jackson、fastjson
    private String serializer = "jackson";

    /**
     * 是否开启全局缓存自动过期
     */
    private Boolean enableTtl;

    /**
     * 全局缓存自动过期时间，单位：秒，enableTtl=true时生效
     */
    private String duration;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getEnableTtl() {
        return enableTtl;
    }

    public void setEnableTtl(Boolean enableTtl) {
        this.enableTtl = enableTtl;
    }

    public String getSerializer() {
        return serializer;
    }

    public void setSerializer(String serializer) {
        this.serializer = serializer;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
