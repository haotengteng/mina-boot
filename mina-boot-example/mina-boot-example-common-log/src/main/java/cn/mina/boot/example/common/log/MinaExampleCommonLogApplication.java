package cn.mina.boot.example.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinaExampleCommonLogApplication {

	private static final Logger logger = LoggerFactory.getLogger(MinaExampleCommonLogApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MinaExampleCommonLogApplication.class, args);
		logger.info("This is a info message1.");
		logger.info("This is a info message2.");
		logger.info("This is a info message3.");
	}


}
