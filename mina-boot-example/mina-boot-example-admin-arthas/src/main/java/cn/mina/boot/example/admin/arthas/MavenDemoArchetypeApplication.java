package cn.mina.boot.example.admin.arthas;

import cn.mina.boot.web.common.MinaBootWebApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MavenDemoArchetypeApplication {

    public static void main(String[] args) throws InterruptedException {
        MinaBootWebApplication.run(MavenDemoArchetypeApplication.class, args);
    }
}
