package cn.mina.boot.oss.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Created by haoteng on 2022/5/27.
 */
@Data
@Component
@ConfigurationProperties(prefix = "mina.oss.minio")
public class MinaOssMinioProperties {

    /**
     * 端点
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;

    /**
     * 桶名称
     */
    private String bucketName;
}
