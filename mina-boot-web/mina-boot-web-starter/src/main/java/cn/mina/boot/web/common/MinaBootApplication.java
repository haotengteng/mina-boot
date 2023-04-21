package cn.mina.boot.web.common;


import cn.mina.boot.web.common.context.MinaWebContextClassRegistrar;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Created by haoteng on 2023/4/21.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootApplication
@Import(MinaWebContextClassRegistrar.class)
public @interface MinaBootApplication {
}
