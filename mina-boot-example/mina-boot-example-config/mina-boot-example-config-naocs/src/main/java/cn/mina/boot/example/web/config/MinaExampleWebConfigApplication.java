package cn.mina.boot.example.web.config;


import cn.mina.boot.example.web.config.controller.ExampleConfig;
import cn.mina.boot.web.common.MinaBootApplication;
import cn.mina.boot.web.common.MinaBootWebApplication;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


/**
 * @NacosPropertySource 注解优先级先配置的高-》后配置的低
 */
@MinaBootApplication
@NacosPropertySource(dataId = "mina-boot.yaml", groupId = "mina-boot", autoRefreshed = true)
@NacosPropertySource(dataId = "mina-boot-base.yaml", groupId = "mina", autoRefreshed = true)
@EnableConfigurationProperties({ExampleConfig.class})
public class MinaExampleWebConfigApplication {

    public static void main(String[] args) {
        MinaBootWebApplication.run(MinaExampleWebConfigApplication.class, args);
    }
}
