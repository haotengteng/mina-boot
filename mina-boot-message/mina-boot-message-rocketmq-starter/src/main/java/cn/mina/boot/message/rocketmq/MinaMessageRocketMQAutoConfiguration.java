package cn.mina.boot.message.rocketmq;

import cn.mina.boot.common.util.ProxyUtil;
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
import org.springframework.context.annotation.*;

import java.lang.reflect.Method;

import static org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration.CONSUMER_BEAN_NAME;
import static org.apache.rocketmq.spring.autoconfigure.RocketMQAutoConfiguration.PRODUCER_BEAN_NAME;

/**
 * rocketmq 消息队列自动配置类
 *
 * @author Created by haoteng on 2022/11/17.
 */
@Configuration
@PropertySource(value = "classpath:mina-boot-message-rocketmq.yml", factory = YmlPropertySourceFactory.class)
public class MinaMessageRocketMQAutoConfiguration implements ApplicationContextAware {

    Logger logger = LoggerFactory.getLogger(MinaMessageRocketMQAutoConfiguration.class);

    private ApplicationContext applicationContext;

    @Bean(destroyMethod = "destroy")
    @ConditionalOnProperty(prefix = "mina.message.rocketmq.template", name = "enhance", havingValue = "true")
    public RocketMQTemplate rocketMQTemplate(RocketMQMessageConverter rocketMQMessageConverter) {
        // 创建原生 RocketMQTemplate
        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
        if (applicationContext.containsBean(PRODUCER_BEAN_NAME)) {
            rocketMQTemplate.setProducer((DefaultMQProducer) applicationContext.getBean(PRODUCER_BEAN_NAME));
        }
        if (applicationContext.containsBean(CONSUMER_BEAN_NAME)) {
            rocketMQTemplate.setConsumer((DefaultLitePullConsumer) applicationContext.getBean(CONSUMER_BEAN_NAME));
        }
        rocketMQTemplate.setMessageConverter(rocketMQMessageConverter.getMessageConverter());
        // 创建增强代理类
        RocketMQTemplate proxy = (RocketMQTemplate) ProxyUtil.proxy(RocketMQTemplate.class, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object result = null;
                try {
                    result = methodProxy.invoke(rocketMQTemplate, objects);
                } catch (Exception e) {
                    // 未重写sendErrorFun情况 原样返回异常
                    SendErrorFun errorFun = MinaRocketMQConfig.getGlobalSendErrorFun();
                    if (errorFun != null) {
                        errorFun.invoke(e);
                    } else {
                        throw new Exception(e);
                    }
                    logger.error("rocketmq send message error ,exception:{}", e.getMessage());
                }
                return result;
            }
        });
        return proxy;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}

