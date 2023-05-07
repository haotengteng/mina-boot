package cn.mina.boot.config.apollo;

import cn.mina.boot.support.YmlPropertySourceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Created by haoteng on 2022/7/26.
 */

@Configuration
@ConditionalOnClass(ApplicationContext.class)
public class MinaConfigApolloAutoConfiguration {

    @Bean
    public MinaConfigApolloContext minaConfigContext() {
        return new MinaConfigApolloContext();
    }
}
