package cn.mina.boot.logging.appender;

import lombok.Data;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.Serializable;

/**
 * @author Created by haoteng on 2022/11/18.
 */
@Data
public class LogKafkaTemplate implements Serializable {

    private KafkaTemplate<String, String> kafkaTemplate;

    private String kafkaLogTopic;
}
