package cn.mina.boot.job.xxl;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Created by haoteng on 2022/10/22.
 */
@Data
@ConfigurationProperties
public class MinaJobXxlProperties {

    @Value("${xxl.job.admin.addresses}")
    private String adminAddresses;
    @Value("${xxl.job.accessToken:#{null}}")
    private String accessToken;
    @Value("${xxl.job.executor.appname}")
    private String appname;
    @Value("${xxl.job.executor.address:#{null}}")
    private String address;
    @Value("${xxl.job.executor.ip:#{null}}")
    private String ip;
    @Value("${xxl.job.executor.port:0}")
    private int port;
    @Value("${xxl.job.executor.logpath}")
    private String logPath;
    @Value("${xxl.job.executor.logretentiondays}")
    private int logRetentionDays;

    @Value("${mina.job.xxl.admin.username}")
    private String username;
    @Value("${mina.job.xxl.admin.password}")
    private String password;
    @Value("${mina.job.xxl.executor.title}")
    private String title;

}
