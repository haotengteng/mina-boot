package cn.mina.boot.logging;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Created by haoteng on 2022/11/18.
 */
@ConfigurationProperties
@Data
public class LogKafkaProperties {

    @Value("${mina.logging.kafka-topic}")
    private String logKafkaTopic;
}
