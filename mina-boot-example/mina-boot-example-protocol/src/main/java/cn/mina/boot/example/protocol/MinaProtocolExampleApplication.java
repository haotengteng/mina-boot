package cn.mina.boot.example.protocol;

import cn.mina.boot.protocol.framework.ProtocolLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class MinaProtocolExampleApplication {


	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(MinaProtocolExampleApplication.class, args);
		TimeUnit.SECONDS.sleep(10);
	}

}
