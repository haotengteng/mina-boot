package cn.mina.web.common.server;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.embedded.undertow.UndertowWebServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Created by haoteng on 2022/7/19.
 */
@Configuration
@ConditionalOnClass(UndertowWebServer.class)
public class MinaWebUndertowAutoConfiguration {

    @Bean
    public UndertowFactoryCustomizer undertowFactoryCustomizer() {
        return new UndertowFactoryCustomizer();
    }
}
