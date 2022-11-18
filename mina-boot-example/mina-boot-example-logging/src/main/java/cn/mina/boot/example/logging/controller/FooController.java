package cn.mina.boot.example.logging.controller;

import cn.mina.boot.example.logging.context.ExampleWebContext;
import cn.mina.boot.example.logging.exception.ExampleErrorCode;
import cn.mina.boot.example.logging.exception.ExampleException;
import cn.mina.boot.web.common.context.MinaWebContextOperator;
import cn.mina.boot.web.common.context.MinaWebResult;
import cn.mina.boot.web.common.context.MinaWebTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/foo")
@Slf4j
public class FooController {

    @GetMapping(path = "hello")
    public String sayHello() throws UnknownHostException {
        String ip = getLocalIP();
        log.info(ip + ": Hello docker!");
        return ip + ": Hello docker!";
    }

    @GetMapping(path = "exception")
    public String testException() {

        throw new ExampleException(ExampleErrorCode.ERROR_INTERNAL_ERROR);
    }

    @GetMapping(path = "hello/client")
    public MinaWebResult<String> sayHelloClientResult() throws UnknownHostException {
        log.info("Hello docker !!!!");
        String ip = getLocalIP();
        return MinaWebTools.response.success(ip + ": Hello docker!!");
    }


    @GetMapping(path = "hello/context")
    public MinaWebResult sayHelloContext() {
        ExampleWebContext context = new ExampleWebContext();
        context.setName("example");
        MinaWebContextOperator.addContext(context);
        log.info("上下文内容:{}", MinaWebTools.context.getContext(ExampleWebContext.class).getName());
        MinaWebContextOperator.removeContext();
        return MinaWebTools.response.success();
    }

    @GetMapping(path = "hello/login")
    public MinaWebResult testLoginToken() {
        ExampleWebContext context = new ExampleWebContext();
        context.setName("example");


        log.info("上下文内容:{}", MinaWebTools.context.getContext(ExampleWebContext.class).getName());
        MinaWebContextOperator.removeContext();
        return MinaWebTools.response.success();
    }

    private String getLocalIP() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        return addr.getHostName() + "-" + addr.getHostAddress();
    }


}
