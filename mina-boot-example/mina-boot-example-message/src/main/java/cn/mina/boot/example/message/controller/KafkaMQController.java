package cn.mina.boot.example.message.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

/**
 * @author Created by haoteng on 2022/11/18.
 */
@RestController
@RequestMapping("kafka")
@Slf4j
public class KafkaMQController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("publish")
    public void publish() {
        kafkaTemplate.send("mina-topic", "message" + System.currentTimeMillis());
    }

    @KafkaListener(topics = "mina-topic",groupId = "mina")
    public void event(ConsumerRecord<String, String> record) {
        Optional<String> kafkaMessage = Optional.ofNullable(record.value());
        kafkaMessage.ifPresent(x -> {
            log.info("消费kafka中的数据:{}...", x);
        });
    }
}
