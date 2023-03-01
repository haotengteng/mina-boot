//package cn.mina.boot.example.message.controller;
//
//import cn.mina.boot.web.common.context.MinaWebResult;
//import cn.mina.boot.web.common.context.MinaWebTools;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.UnknownHostException;
//import java.util.Optional;
//
///**
// * @author Created by haoteng on 2022/11/18.
// */
//@RestController
//@RequestMapping("kafka")
//@Slf4j
//public class KafkaMQController {
//
//    @Autowired(required = false)
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @GetMapping("publish")
//    public void publish() {
//        kafkaTemplate.send("mina-topic", "message" + System.currentTimeMillis());
//    }
//
//    @GetMapping(path = "hello/client")
//    public MinaWebResult<String> sayHelloClientResult() throws UnknownHostException {
//        log.info("Hello docker !!!!");
//        return MinaWebTools.response.success(": Hello docker!!");
//    }
//
//    @GetMapping(path = "hello/context")
//    public MinaWebResult sayHelloContext() {
//
//        return MinaWebTools.response.success();
//    }
//
//    @KafkaListener(topics = "mina-topic", groupId = "mina")
//    public void event(ConsumerRecord<String, String> record) {
//        Optional<String> kafkaMessage = Optional.ofNullable(record.value());
//        kafkaMessage.ifPresent(x -> {
//            log.info("消费kafka中的数据:{}...", x);
//        });
//    }
//}
