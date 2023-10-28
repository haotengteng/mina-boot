package cn.mina.boot.example.web.data.hugegraph.controller;

import org.apache.hugegraph.driver.HugeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author Created by haoteng on 2023/10/11.
 */
@Component
public class FooController implements CommandLineRunner {

    @Autowired
    private HugeClient hugeClient;


    @Override
    public void run(String... args) throws Exception {

        hugeClient.schema();
        System.out.println("111");
    }
}
