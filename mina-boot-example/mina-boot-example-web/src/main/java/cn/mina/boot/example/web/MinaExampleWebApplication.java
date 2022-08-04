package cn.mina.boot.example.web;

import cn.mina.boot.context.MinaBootApplication;
import cn.mina.boot.example.web.context.ExampleWebContext;
import cn.mina.boot.web.common.context.MinaWebContextOperator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinaExampleWebApplication {

    public static void main(String[] args) {
        MinaBootApplication.run(MinaExampleWebApplication.class, args);
        // 设置自定义上下文
        MinaWebContextOperator.initCustomContext(ExampleWebContext.class);
    }

}