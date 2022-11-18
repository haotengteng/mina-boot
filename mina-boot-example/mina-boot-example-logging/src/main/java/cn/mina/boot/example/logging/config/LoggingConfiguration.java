package cn.mina.boot.example.logging.config;

import cn.mina.boot.logging.appender.LogKafkaTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @author Created by haoteng on 2022/11/18.
 */
@Configuration
public class LoggingConfiguration {

    @Bean
    public LogKafkaTemplate logKafkaTemplate(@Autowired KafkaTemplate<String, String> kafkaTemplate) {
        LogKafkaTemplate logKafkaTemplate = new LogKafkaTemplate();
        logKafkaTemplate.setKafkaTemplate(kafkaTemplate);
        return logKafkaTemplate;
    }
}
