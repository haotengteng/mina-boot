package cn.mina.boot.example.common.docker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/foo")
public class FooController {

    @GetMapping(path = "hello")
    public String sayHello() throws UnknownHostException {
        String ip = getLocalIP();
        System.out.println(342);
        return ip + ": Hello docker!";
    }

    private String getLocalIP() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        return addr.getHostName() + "-" + addr.getHostAddress();
    }
}
