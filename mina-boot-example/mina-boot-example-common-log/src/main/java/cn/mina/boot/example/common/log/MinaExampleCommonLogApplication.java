package cn.mina.boot.example.common.log;

import cn.mina.boot.MinaApplication;
import cn.mina.boot.context.MinaBootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
@Component
public class MinaExampleCommonLogApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(MinaExampleCommonLogApplication.class);
    @Autowired
    private MinaBootContext minaBootContext;

    public static void main(String[] args) throws InterruptedException, IOException {
        MinaApplication.run(MinaExampleCommonLogApplication.class, args);
        while (true) {
            TimeUnit.SECONDS.sleep(100);
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
