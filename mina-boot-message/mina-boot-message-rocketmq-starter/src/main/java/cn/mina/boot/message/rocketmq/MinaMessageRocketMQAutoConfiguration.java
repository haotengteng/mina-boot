package cn.mina.boot.message.rocketmq;

import cn.mina.boot.common.util.ProxyUtils;
import cn.mina.boot.support.YmlPropertySourceFactory;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.lang.reflect.Method;

import static org.apache.rocketmq.client.log.ClientLogger.CLIENT_LOG_USESLF4J;
import static org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration.CONSUMER_BEAN_NAME;
import static org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration.PRODUCER_BEAN_NAME;

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
        System.setProperty(CLIENT_LOG_USESLF4J,"true");
    }
}

