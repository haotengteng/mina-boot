package cn.mina.web.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Created by haoteng on 2022/7/19.
 */
@Configuration
@ConditionalOnWebApplication
public class MinaWebExceptionHandlerAutoConfiguration {

    @Bean
    public WebExceptionHandler webExceptionHandler() {
        return new WebExceptionHandler();
    }
}
