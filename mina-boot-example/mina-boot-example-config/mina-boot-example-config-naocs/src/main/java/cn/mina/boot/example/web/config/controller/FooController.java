package cn.mina.boot.example.web.config.controller;

import cn.mina.boot.config.nacos.MinaConfigNacosContext;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/foo")
public class FooController implements CommandLineRunner {

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
    private MinaConfigNacosContext minaConfigNacosContext;

    @GetMapping(path = "hello")
    public String sayHello() throws UnknownHostException {
        log.info("nacos-config 监听到配置信息为:{}", name);
        log.info("nacos-config 监听到配置信息为:{}", address);
        log.info("nacos-config 监听到配置信息为:{}", age);
        log.info("nacos-config 监听到配置信息为:{}", exampleConfig.getName());
        log.info("nacos-config 监听到配置信息为:{}", exampleConfig.getAddress());
        log.info("nacos-config 监听到配置信息为:{}", minaConfigNacosContext.getProperty("mina.user.name"));
        log.info("nacos-config 监听到配置信息为:{}", minaConfigNacosContext.getProperty("mina.user.address"));

        String ip = getLocalIP();
        return ip + ": Hello docker!";
    }

    private String getLocalIP() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        return addr.getHostName() + "-" + addr.getHostAddress();
    }

    @Override
    public void run(String... args) throws Exception {
        new Thread(()->{
            try {
                while (true){
                    TimeUnit.SECONDS.sleep(3);
                    this.sayHello();
                }
            } catch (UnknownHostException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
