package cn.mina.boot.web.common.logging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Created by haoteng on 2022/7/19.
 */
@Configuration
@EnableConfigurationProperties({MinaWebLogProperties.class})
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "mina.web.logger", name = "enable", havingValue = "true", matchIfMissing = true)
public class MinaWebLogFilterAutoConfiguration {

    @Autowired
    private MinaWebLogProperties minaWebLogProperties;

    @Bean
    public MinaWebLoggingFilter minaWebLoggingFilter() {
        MinaWebLoggingFilter minaWebLoggingFilter = new MinaWebLoggingFilter();
        minaWebLoggingFilter.setExcludeUrls(minaWebLogProperties.getExcludeUrls());
        return minaWebLoggingFilter;
    }

}
