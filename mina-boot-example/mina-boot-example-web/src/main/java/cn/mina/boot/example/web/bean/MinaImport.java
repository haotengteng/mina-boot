package cn.mina.boot.example.web.bean;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(TestImportBeanDefinitionRegistrar.class)
public @interface MinaImport {
}
