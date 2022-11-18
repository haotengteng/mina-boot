package cn.mina.boot.example.logging;

import cn.mina.boot.context.MinaBootApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinaExampleLoggingApplication {

    public static void main(String[] args) {
        MinaBootApplication.run(MinaExampleLoggingApplication.class, args);
    }

}