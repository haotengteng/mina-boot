package cn.mina.boot.example.web.config.controller;

import cn.mina.config.nacos.MinaConfigContext;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/foo")
public class FooController {

    Logger log = LoggerFactory.getLogger(FooController.class);

    @NacosValue(value = "${mina.user.name:mina}", autoRefreshed = true)
    private String name;

    @NacosValue("${mina.user.address:hangzhou}")
    private String address;

    @NacosValue(value = "${mina.user.age:1}", autoRefreshed = true)
    private Integer age;

    @Autowired
    private ExampleConfig exampleConfig;

    @Autowired
    private MinaConfigContext minaConfigContext;

    @GetMapping(path = "hello")
    public String sayHello() throws UnknownHostException {
        log.info("nacos-config 监听到配置信息为:{}", name);
        log.info("nacos-config 监听到配置信息为:{}", address);
        log.info("nacos-config 监听到配置信息为:{}", age);
        log.info("nacos-config 监听到配置信息为:{}", exampleConfig.getName());
        log.info("nacos-config 监听到配置信息为:{}", exampleConfig.getAddress());
        log.info("nacos-config 监听到配置信息为:{}", minaConfigContext.getProperty("mina.user.name"));
        log.info("nacos-config 监听到配置信息为:{}", minaConfigContext.getProperty("mina.user.address"));

        String ip = getLocalIP();
        return ip + ": Hello docker!";
    }

    private String getLocalIP() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        return addr.getHostName() + "-" + addr.getHostAddress();
    }
}
