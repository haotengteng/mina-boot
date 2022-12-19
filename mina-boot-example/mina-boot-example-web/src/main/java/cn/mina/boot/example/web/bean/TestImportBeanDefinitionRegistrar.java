package cn.mina.boot.example.web.bean;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;


public class TestImportBeanDefinitionRegistrar  implements ImportBeanDefinitionRegistrar {

    public  void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        System.out.println(importingClassMetadata.getClassName());
        System.out.println(registry.getBeanDefinitionCount());
    }

}
