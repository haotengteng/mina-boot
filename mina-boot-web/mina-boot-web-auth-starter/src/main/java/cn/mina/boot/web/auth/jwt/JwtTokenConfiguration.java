package cn.mina.boot.web.auth.jwt;

import cn.mina.boot.web.auth.MinaTokenProperties;
import cn.mina.boot.web.auth.MinaWebAuthTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author Created by haoteng on 2022/7/28.
 */
@Configuration
@ConditionalOnExpression("'${mina.web.auth.type}'.equals('jwt')")
public class JwtTokenConfiguration {

    @Autowired
    private MinaTokenProperties properties;

    @Bean
    public JwtTokenInterceptor jwtTokenInterceptor() {
        return new JwtTokenInterceptor(properties);
    }

    @PostConstruct
    public void init() {
        MinaWebAuthTools.token.generator = t -> JwtTokenUtil.encode(t, Integer.valueOf(properties.getExpireTime()), properties.getSecret());
    }

}
