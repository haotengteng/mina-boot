package cn.mina.boot.protocol.http.accepter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;

/**
 * @author Created by haoteng on 2023/2/21.
 */
@Configuration
@ConditionalOnClass(Servlet.class)
public class MinaProtocolHttpAccepterAutoConfiguration {

    @Bean
    public HttpProtocolAccepter httpProtocolAccepter() {
        return new HttpProtocolAccepter();
    }

}
