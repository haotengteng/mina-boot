package cn.mina.boot.example.admin.server;

import cn.mina.boot.MinaApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinaExamplAdminClientApplication {

    public static void main(String[] args) {
        MinaApplication.run(MinaExamplAdminClientApplication.class, args);
    }
}