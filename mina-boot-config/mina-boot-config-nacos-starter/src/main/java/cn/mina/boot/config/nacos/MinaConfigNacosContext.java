package cn.mina.boot.config.nacos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;

/**
 * @author Created by haoteng on 2022/7/26.pri
 */
public class MinaConfigNacosContext implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(MinaConfigNacosContext.class);

    private Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        environment = applicationContext.getEnvironment();
        log.info("mina config info has been loaded success !");
    }

    public <T> T getProperty(String key, Class<T> tClass) {
        return environment.getProperty(key, tClass);
    }

    public String getProperty(String key) {
        return environment.getProperty(key);
    }
}
