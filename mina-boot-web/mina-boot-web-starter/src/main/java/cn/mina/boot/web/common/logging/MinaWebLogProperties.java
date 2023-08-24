package cn.mina.boot.web.common.logging;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author created by htt on 2018/8/9
 */
@ConfigurationProperties(prefix = "mina.web.logger")
public class MinaWebLogProperties {
    /**
     * http请求响应日志打印开关，默认关闭
     */
    private Boolean enable = false;

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
