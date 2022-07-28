package cn.mina.web.common.log;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author created by htt on 2018/8/9
 */
@ConfigurationProperties(prefix = "mina.web.log")
public class MinaWebLogProperties {
    /**
     * http请求响应日志打印开关，默认关闭
     */
    private Boolean enable = false;
    // TODO: 2022/7/20 实现脱敏
    private Boolean desensitization = false;

    private String[] excludeUrls;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String[] getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }
}
