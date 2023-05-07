package cn.mina.boot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class MinaApplication {
    private static final Logger logger = LoggerFactory.getLogger(MinaApplication.class);

    public static ConfigurableApplicationContext run(Class<?> primarySource, String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(primarySource, args);
        logger.info("Mina project start succeeded !!!");
        return applicationContext;
    }
}
