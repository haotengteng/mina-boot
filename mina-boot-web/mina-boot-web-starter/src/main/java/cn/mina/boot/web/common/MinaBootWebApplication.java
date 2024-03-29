package cn.mina.boot.web.common;

import cn.mina.boot.MinaApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

public class MinaBootWebApplication {

    private static final Logger logger = LoggerFactory.getLogger(MinaBootWebApplication.class);

    public static ConfigurableApplicationContext run(Class<?> primarySource, String[] args) {
        return MinaApplication.run(primarySource, args);
    }
}
