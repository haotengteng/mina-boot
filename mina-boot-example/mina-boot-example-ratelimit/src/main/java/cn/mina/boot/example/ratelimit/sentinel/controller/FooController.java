package cn.mina.boot.example.ratelimit.sentinel.controller;

import cn.mina.boot.web.common.exception.MinaGlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/foo")
@Slf4j
public class FooController {

    @GetMapping(path = "hello")
    public String sayHello(@RequestParam("name") String name) throws UnknownHostException, InterruptedException {
        String ip = getLocalIP();
        if ("1".equals(name)) {
            TimeUnit.SECONDS.sleep(1);
        }
        log.info(ip + ": Hello docker!");
        return ip + ": Hello docker!";
    }


    private int count = 0;

    @GetMapping(path = "hello1")
    public String sayHello1() throws UnknownHostException, InterruptedException {
        String ip = getLocalIP();
        count++;
        if (0 == (count % 2)) {
            throw new MinaGlobalException();
        }
        log.info(ip + ": Hello docker!");
        return ip + ": Hello docker!";
    }


    private String getLocalIP() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        return addr.getHostName() + "-" + addr.getHostAddress();
    }

}
