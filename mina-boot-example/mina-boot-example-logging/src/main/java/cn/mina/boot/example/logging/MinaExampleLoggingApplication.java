package cn.mina.boot.example.logging;

import cn.mina.boot.MinaApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinaExampleLoggingApplication {

    public static void main(String[] args) {
        MinaApplication.run(MinaExampleLoggingApplication.class, args);
    }

}