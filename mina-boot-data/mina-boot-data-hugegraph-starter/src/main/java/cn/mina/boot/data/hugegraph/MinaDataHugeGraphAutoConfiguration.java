package cn.mina.boot.data.hugegraph;

import cn.mina.boot.support.YmlPropertySourceFactory;
import org.apache.hugegraph.driver.HugeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * rocketmq 消息队列自动配置类
 *
 * @author Created by haoteng on 2022/11/17.
 */
@Configuration
@EnableConfigurationProperties(MinaDataHugeGraphProperties.class)
@PropertySource(value = "classpath:mina-boot-data-hugegraph.yml", factory = YmlPropertySourceFactory.class)
public class MinaDataHugeGraphAutoConfiguration {

    @Autowired
    private MinaDataHugeGraphProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public HugeClient hugeClient() {
        return HugeClient.builder(properties.getUrl(), properties.getGraph())
                // 默认 20s 超时
                .configTimeout(20)
                .configUser(properties.getUsername(), properties.getPassword())
                .build();
    }
}

