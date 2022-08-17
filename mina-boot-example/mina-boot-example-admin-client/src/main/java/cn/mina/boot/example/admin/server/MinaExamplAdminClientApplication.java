package cn.mina.boot.example.admin.server;

import cn.mina.boot.context.MinaBootApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinaExamplAdminClientApplication {

    public static void main(String[] args) {
        MinaBootApplication.run(MinaExamplAdminClientApplication.class, args);
    }
}