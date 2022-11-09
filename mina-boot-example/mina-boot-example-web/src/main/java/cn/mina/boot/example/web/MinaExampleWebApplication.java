package cn.mina.boot.example.web;

import cn.mina.boot.context.MinaBootApplication;
import cn.mina.boot.example.web.context.ExampleWebContext;
import cn.mina.boot.web.common.MinaBootWebApplication;
import cn.mina.boot.web.common.context.MinaWebContextOperator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
// 开启@WebServlet、@WebFilter、@WebListener注解支持
@ServletComponentScan
public class MinaExampleWebApplication {

    public static void main(String[] args) {
        MinaBootWebApplication.run(MinaExampleWebApplication.class, args,ExampleWebContext.class);
        // 设置自定义上下文
//        MinaWebContextOperator.initCustomContext(ExampleWebContext.class);
    }

}