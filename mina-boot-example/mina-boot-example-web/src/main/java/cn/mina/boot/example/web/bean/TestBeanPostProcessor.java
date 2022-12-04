package cn.mina.boot.example.web.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TestBeanPostProcessor implements BeanPostProcessor {


    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("BeforeBeanPostProcessor==>"+beanName);
        return bean;
    }


    public  Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("AfterBeanPostProcessor==>"+beanName);
        return bean;
    }
}
