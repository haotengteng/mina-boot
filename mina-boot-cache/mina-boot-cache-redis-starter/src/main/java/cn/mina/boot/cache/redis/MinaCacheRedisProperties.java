package cn.mina.boot.cache.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Created by haoteng on 2022/7/22.
 */
@ConfigurationProperties(prefix = "mina.cache.redis")
public class MinaCacheRedisProperties {

    private String enable = "true";

    // jackson、fastjson
    private String serializer = "jackson";

    /**
     * 是否开启全局缓存自动过期
     */
    private String enableTtl;

    /**
     * 全局缓存自动过期时间，单位：秒，enableTtl=true时生效
     */
    private String duration;

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getEnableTtl() {
        return enableTtl;
    }

    public void setEnableTtl(String enableTtl) {
        this.enableTtl = enableTtl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
