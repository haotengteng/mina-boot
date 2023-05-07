package cn.mina.boot.example.config.apollo.conf;

import cn.mina.boot.config.apollo.MinaConfigApolloContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Created by haoteng on 2023/5/6.
 */
@Component
public class ApolloConfigTest implements CommandLineRunner {

    @Value("${mina.config.apollo.name:mina}")
    private String name;
    @Value("${mina.config.apollo.age:0}")
    private String age;

    @Autowired
    private MinaConfigApolloContext minaConfigApolloContext;

    @Override
    public void run(String... args) throws Exception {
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(this.name + "|" + this.age);
                    System.out.println(minaConfigApolloContext.getProperty("mina.config.apollo.address"));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
