package cn.mina.boot.example.ratelimit.sentinel;

import cn.mina.boot.MinaApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinaExampleRatelimitApplication {

    public static void main(String[] args) {
        MinaApplication.run(MinaExampleRatelimitApplication.class, args);
    }

}
