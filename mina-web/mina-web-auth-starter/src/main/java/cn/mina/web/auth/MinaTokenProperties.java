package cn.mina.web.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Created by haoteng on 2022/7/21.
 */
@ConfigurationProperties(prefix = "mina.web.auth")
public class MinaTokenProperties {


    private String enable;
    /**
     *  jwt 、 redis 、memory
     */
    private String type;
    /**
     * token :携带head的key
     */
    private String key = "token";
    /**
     * token 秘钥
     */
    private String secret;

    /**
     * 超时时间，单位小时，支持小数
     */
    private String expireTime;

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }
}
