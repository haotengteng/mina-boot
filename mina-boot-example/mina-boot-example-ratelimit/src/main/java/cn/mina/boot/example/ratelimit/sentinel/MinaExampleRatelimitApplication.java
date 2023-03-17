package cn.mina.boot.example.ratelimit.sentinel;

import cn.mina.boot.MinaBootApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinaExampleRatelimitApplication {

    public static void main(String[] args) {
        MinaBootApplication.run(MinaExampleRatelimitApplication.class, args);
    }

}
