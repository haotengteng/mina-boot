package cn.mina.boot.example.web;

import cn.mina.boot.web.common.MinaBootApplication;
import cn.mina.boot.web.common.MinaBootWebApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@MinaBootApplication
// 开启@WebServlet、@WebFilter、@WebListener注解支持
@ServletComponentScan
public class MinaExampleWebApplication {

    public static void main(String[] args) {
        MinaBootWebApplication.run(MinaExampleWebApplication.class, args);
    }

}