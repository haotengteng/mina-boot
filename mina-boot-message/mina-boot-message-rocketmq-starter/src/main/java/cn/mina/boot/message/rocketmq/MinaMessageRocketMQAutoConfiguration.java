package cn.mina.boot.message.rocketmq;

import cn.mina.boot.support.YmlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * rocketmq 消息队列自动配置类
 *
 * @author Created by haoteng on 2022/11/17.
 */
@Configuration
@PropertySource(value = "classpath:mina-boot-message-rocketmq.yml", factory = YmlPropertySourceFactory.class)
public class MinaMessageRocketMQAutoConfiguration {

}

