package cn.mina.boot.oss.minio;

import cn.mina.boot.support.YmlPropertySourceFactory;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Created by haoteng on 2022/10/17.
 */
@Configuration
@EnableConfigurationProperties({MinaOssMinioProperties.class})
@PropertySource(value = "classpath:mina-oss-minio.yml", factory = YmlPropertySourceFactory.class)
@ConditionalOnProperty(prefix = "mina.oss.minio", name = "enable", havingValue = "true", matchIfMissing = true)
public class MinaOssMinioAutoConfiguration {

    @Autowired
    private MinaOssMinioProperties minaOssMinioProperties;


    /**
     * 初始化 MinIO 客户端
     */
    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient = MinioClient.builder()
                .endpoint(minaOssMinioProperties.getEndpoint())
                .credentials(minaOssMinioProperties.getAccessKey(), minaOssMinioProperties.getSecretKey())
                .build();
        initMinaOssMinio(minioClient);
        return minioClient;
    }


    private void initMinaOssMinio(MinioClient minioClient) {
        MinaOssMinioUtil.minioClient = minioClient;
        MinaOssMinioUtil.bucketName = minaOssMinioProperties.getBucketName();
        // 初始化桶，不存在则创建
        MinaOssMinioUtil.initBucket();
    }

}
