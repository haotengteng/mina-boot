package cn.mina.boot.example.message;

import cn.mina.boot.web.common.MinaBootWebApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinaExampleWebApplication {

    public static void main(String[] args) {
        MinaBootWebApplication.run(MinaExampleWebApplication.class, args);
//        SpringApplication.run(MinaExampleWebApplication.class,args);
    }

}