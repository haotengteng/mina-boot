package cn.mina.boot.example.web.config.controller;

import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @NacosConfigurationProperties与@ConfigurationProperties一起使用可以实现动态刷新
 * @NacosConfigurationProperties获取指定的dataId的配置
 * @author Created by haoteng on 2022/7/26.
 */


@NacosConfigurationProperties(prefix = "mina.user", dataId = "mina-boot.yaml", groupId = "mina-boot", autoRefreshed = true)
@Data
@ConfigurationProperties
public class ExampleConfig {
    private String name;

    private String address;
}
