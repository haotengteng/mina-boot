package cn.mina.boot.message.rocketmq;

import cn.mina.boot.support.YmlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import static org.apache.rocketmq.client.log.ClientLogger.CLIENT_LOG_USESLF4J;

/**
 * rocketmq 消息队列自动配置类
 *
 * @author Created by haoteng on 2022/11/17.
 */
@Configuration
@PropertySource(value = "classpath:mina-boot-message-rocketmq.yml", factory = YmlPropertySourceFactory.class)
public class MinaMessageRocketMQAutoConfiguration {

    //    rocketmq client使用系统默认日志配置
    static {
        System.setProperty(CLIENT_LOG_USESLF4J, "true");
    }
}

