package cn.mina.example.web;

import cn.mina.boot.MinaBootApplication;
import cn.mina.example.web.context.ExampleWebContext;
import cn.mina.web.common.context.MinaWebContextOperator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinaExampleWebApplication {

    public static void main(String[] args) {
        MinaBootApplication.run(MinaExampleWebApplication.class, args);
        // 设置自定义上下文
        MinaWebContextOperator.initCustomContext(ExampleWebContext.class);
    }

}