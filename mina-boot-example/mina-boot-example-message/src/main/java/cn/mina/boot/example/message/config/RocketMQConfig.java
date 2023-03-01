package cn.mina.boot.example.message.config;

import cn.mina.boot.message.rocketmq.MinaRocketMQConfig;
import cn.mina.boot.message.rocketmq.MinaRocketMQProxy;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;

/**
 * @author Created by haoteng on 2023/3/1.
 */
@Configuration
public class RocketMQConfig {

    /**
     * 自定义RocketMQTemplate 异常处理
     *
     * @param rocketMQTemplate
     * @return
     */
    @Bean(name = "RocketMQTemplateDiy",destroyMethod = "destroy")
    @Lazy
    public RocketMQTemplate getRocketMQTemplate(@Autowired RocketMQTemplate rocketMQTemplate) {
        return MinaRocketMQProxy.enhance(rocketMQTemplate, e -> {
            System.out.println("RocketMQTemplateDiy----,e:{}" + e.getMessage());
        });
    }

    /**
     * 初始化全局异常处理
     */
    @PostConstruct
    public void init() {
        MinaRocketMQConfig.setGlobalSendErrorFun((e -> {
            System.out.println(e.getMessage());
        }));
    }
}
