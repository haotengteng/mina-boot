package cn.mina.boot.example.protocol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class MinaProtocolExampleApplication {


    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(MinaProtocolExampleApplication.class, args);
        TimeUnit.SECONDS.sleep(10);
    }

}
