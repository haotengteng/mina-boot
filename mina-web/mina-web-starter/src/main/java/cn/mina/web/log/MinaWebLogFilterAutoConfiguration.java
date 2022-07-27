package cn.mina.web.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Created by haoteng on 2022/7/19.
 */
@Configuration
@EnableConfigurationProperties({MinaWebLogProperties.class})
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "mina.web.log", name = "enable", havingValue = "true", matchIfMissing = true)
public class MinaWebLogFilterAutoConfiguration {

    @Autowired
    private MinaWebLogProperties minaWebLogProperties;

    /**
     * 装配web日志过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean<MinaWebLogFilter> minaWebLogFilter() {
        MinaWebLogFilter logFilter = new MinaWebLogFilter(minaWebLogProperties);
        FilterRegistrationBean<MinaWebLogFilter> filterRegistrationBean = new FilterRegistrationBean<>(
                logFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        String[] excludeUrls = minaWebLogProperties.getExcludeUrls();
        if (excludeUrls!=null){
            filterRegistrationBean.addInitParameter("excludeUrls",String.join(",",excludeUrls).toString());
        }
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}
