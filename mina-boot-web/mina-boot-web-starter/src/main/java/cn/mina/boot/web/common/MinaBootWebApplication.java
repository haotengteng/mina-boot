package cn.mina.boot.web.common;

import cn.mina.boot.context.MinaBootApplication;
import cn.mina.boot.web.common.context.MinaWebContext;
import cn.mina.boot.web.common.context.MinaWebContextOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

public class MinaBootWebApplication {
    private static final Logger logger = LoggerFactory.getLogger(MinaBootWebApplication.class);

    public static ConfigurableApplicationContext run(Class<?> primarySource, String[] args, Class<? extends MinaWebContext> context) {
        ConfigurableApplicationContext applicationContext = MinaBootApplication.run(MinaBootWebApplication.class, args);
        // 设置自定义上下文
        MinaWebContextOperator.initCustomContext(context);
        return applicationContext;
    }
}
