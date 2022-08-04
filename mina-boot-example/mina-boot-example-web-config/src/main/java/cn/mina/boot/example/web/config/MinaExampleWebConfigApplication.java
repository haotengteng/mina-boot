package cn.mina.boot.example.web.config;

import cn.mina.boot.context.MinaBootApplication;
import cn.mina.boot.example.web.config.controller.ExampleConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ExampleConfig.class)
public class MinaExampleWebConfigApplication {

    public static void main(String[] args) {
        MinaBootApplication.run(MinaExampleWebConfigApplication.class, args);
    }
}
