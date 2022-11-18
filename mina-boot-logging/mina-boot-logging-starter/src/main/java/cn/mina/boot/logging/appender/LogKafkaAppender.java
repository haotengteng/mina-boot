package cn.mina.boot.logging.appender;

import cn.mina.boot.common.log.LogBaseAppender;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * 收集日志 输出到kafka实现
 *
 * @author Created by haoteng on 2022/6/17.
 */
public class LogKafkaAppender extends LogBaseAppender {

    private static KafkaTemplate<String, String> kafkaTemplate;

    private static String kafkaLogTopic;


    @Override
    public void output(String log) {
        if (kafkaTemplate != null) {
            kafkaTemplate.send(kafkaLogTopic, log);
        }
    }

    public static KafkaTemplate<String, String> getKafkaTemplate() {
        return kafkaTemplate;
    }

    public static String getKafkaLogTopic() {
        return kafkaLogTopic;
    }

    public static void setKafkaLogTopic(String kafkaLogTopic) {
        LogKafkaAppender.kafkaLogTopic = kafkaLogTopic;
    }

    public static void setKafkaTemplate(KafkaTemplate<String, String> kafkaTemplate) {
        LogKafkaAppender.kafkaTemplate = kafkaTemplate;
    }
}
