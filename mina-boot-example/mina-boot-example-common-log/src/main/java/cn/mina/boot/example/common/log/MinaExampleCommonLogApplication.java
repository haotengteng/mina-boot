package cn.mina.boot.example.common.log;

import cn.mina.boot.MinaBootApplication;
import cn.mina.boot.context.MinaBootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Component
public class MinaExampleCommonLogApplication implements CommandLineRunner {

    @Autowired
    private MinaBootContext minaBootContext;

    private static final Logger logger = LoggerFactory.getLogger(MinaExampleCommonLogApplication.class);

    public static void main(String[] args) throws InterruptedException {
        MinaBootApplication.run(MinaExampleCommonLogApplication.class, args);
        while (true) {
            TimeUnit.SECONDS.sleep(10);
            logger.debug("This is a info message1.");
            logger.info("This is a info message2.");
            logger.error("This is a info message3.");
        }
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info(minaBootContext.getApplicationContext().getClassLoader().toString());
        logger.info(minaBootContext.getEnvironment().getProperty("server.port"));
    }


}
