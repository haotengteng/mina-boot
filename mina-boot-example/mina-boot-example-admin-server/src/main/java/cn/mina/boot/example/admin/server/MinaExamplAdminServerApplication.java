package cn.mina.boot.example.admin.server;

import cn.mina.boot.MinaApplication;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAdminServer
public class MinaExamplAdminServerApplication {

    public static void main(String[] args) {
        MinaApplication.run(MinaExamplAdminServerApplication.class, args);
    }
}