package cn.mina.boot.example.web.config;

import cn.mina.boot.context.MinaBootApplication;
import cn.mina.boot.example.web.config.controller.ExampleConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


/**
 * @NacosPropertySource 注解优先级先配置的高-》后配置的低
 */
@SpringBootApplication
@NacosPropertySource(dataId = "mina-boot.yaml", groupId = "mina-boot", autoRefreshed = true)
@NacosPropertySource(dataId = "mina-boot-base.yaml", groupId = "mina", autoRefreshed = true)
@EnableConfigurationProperties({ExampleConfig.class})
public class MinaExampleWebConfigApplication {

    public static void main(String[] args) {
        MinaBootApplication.run(MinaExampleWebConfigApplication.class, args);
    }
}
