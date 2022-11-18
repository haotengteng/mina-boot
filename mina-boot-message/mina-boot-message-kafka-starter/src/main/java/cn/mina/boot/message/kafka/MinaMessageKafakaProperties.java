package cn.mina.boot.message.kafka;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Created by haoteng on 2022/11/18.
 */
@ConfigurationProperties
@Data
public class MinaMessageKafakaProperties {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;
    @Value("${spring.kafka.producer.retries}")
    private Integer retries;
    @Value("${spring.kafka.producer.batch-size}")
    private Integer batchSize;
    @Value("${spring.kafka.producer.buffer-memory}")
    private Integer bufferMemory;
    @Value("${spring.kafka.producer.acks}")
    private String acks;
    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;
    @Value("${spring.kafka.consumer.max-poll-records}")
    private Integer maxPollRecords;
    @Value("${spring.kafka.consumer.enable-auto-commit}")
    private Boolean autoCommit;
    @Value("${spring.kafka.consumer.auto-commit-interval}")
    private Integer autoCommitInterval;
    @Value("${spring.kafka.listener.concurrency}")
    private Integer concurrency;
}
