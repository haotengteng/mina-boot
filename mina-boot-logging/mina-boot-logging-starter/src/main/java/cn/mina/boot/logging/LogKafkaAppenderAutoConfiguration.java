package cn.mina.boot.logging;

import cn.mina.boot.common.exception.MinaBaseException;
import cn.mina.boot.logging.appender.LogKafkaAppender;
import cn.mina.boot.logging.appender.LogKafkaTemplate;
import cn.mina.boot.support.YmlPropertySourceFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

/**
 * LogKafkaAppender自动配置类
 *
 * @author Created by haoteng on 2022/11/17.
 */
@Configuration
@ConditionalOnBean(LogKafkaTemplate.class)
@EnableConfigurationProperties(LogKafkaProperties.class)
@PropertySource(value = "classpath:mina-boot-logging.yml", factory = YmlPropertySourceFactory.class)
public class LogKafkaAppenderAutoConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private LogKafkaProperties properties;

    //todo 优化LogKafkaTemplate 对象生成方式，采用 ConditionalOnMissBean方式，默认生成
    @PostConstruct
    public void setProperties() {
        LogKafkaTemplate logKafkaTemplate = applicationContext.getBean(LogKafkaTemplate.class);
        LogKafkaAppender.setKafkaTemplate(logKafkaTemplate.getKafkaTemplate());
        if (StringUtils.isNoneBlank(logKafkaTemplate.getKafkaLogTopic())) {
            LogKafkaAppender.setKafkaLogTopic(logKafkaTemplate.getKafkaLogTopic());
        } else {
            LogKafkaAppender.setKafkaLogTopic(properties.getLogKafkaTopic());
        }
        if (StringUtils.isBlank(LogKafkaAppender.getKafkaLogTopic())) {
            throw new MinaBaseException("日志收集topic未指定...");
        }
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
