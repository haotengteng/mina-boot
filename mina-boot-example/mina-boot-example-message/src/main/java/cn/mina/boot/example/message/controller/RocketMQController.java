//package cn.mina.boot.example.message.controller;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.apache.rocketmq.spring.core.RocketMQTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//
//@RestController
//@RequestMapping("/foo")
//@Slf4j
//public class RocketMQController {
//
//
//    @Autowired
//    private RocketMQTemplate rocketMQTemplate;
//
//    /**
//     * 发送消息
//     *
//     * @return
//     * @throws UnknownHostException
//     */
//    @GetMapping(path = "rocketmq")
//    public String sayHello() throws UnknownHostException {
//        String ip = getLocalIP();
//        //send spring message
//        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload(ip + ": Hello docker!").build());
//        System.out.println("============success=========");
//        return "";
//    }
//
//    private String getLocalIP() throws UnknownHostException {
//        InetAddress addr = InetAddress.getLocalHost();
//        return addr.getHostName() + "-" + addr.getHostAddress();
//    }
//
//    /**
//     * 消费消息
//     *
//     * @return
//     */
//    @Component
//    @RocketMQMessageListener(consumerGroup = "MinaConsumerGroup", topic = "test-topic-1",maxReconsumeTimes=3)
//    public class SpringConsumer implements RocketMQListener<String> {
//        @Override
//        public void onMessage(String message) {
//            log.info("Recived message: {}", message);
////            throw new MinaGlobalException(GlobalErrorCode.ERROR_SYS_ERROR);
//        }
//    }
//
//}
