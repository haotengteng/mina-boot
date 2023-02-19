package cn.mina.boot.context;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * spring 相关工具bean 包装
 *
 * @author Created by haoteng on 2023/2/19.
 */
@Configuration
public class MinaBootContextAutoConfiguration {

    /**
     * 提供 applicationContext，environment等核心bean
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "mina.boot.context", name = "enable", havingValue = "true", matchIfMissing = true)
    public MinaBootContext getMiniBootContext() {
        return new MinaBootContext();
    }
}
