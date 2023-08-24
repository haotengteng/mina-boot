package cn.mina.boot.web.common.filter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by haoteng on 2022/7/19.
 */
@Configuration
@ConditionalOnWebApplication
public class MinaWebFilterAutoConfiguration {


    /**
     * 装配web日志过滤器
     *
     * @return
     */
    @Bean
    public List<FilterRegistrationBean> minaWebLogFilter(List<MinaOncePerRequestFilter> filters) {

        return filters.stream().map(filter -> {
            FilterRegistrationBean<MinaOncePerRequestFilter> filterRegistrationBean = new FilterRegistrationBean<>();
            filterRegistrationBean.setFilter(filter);
            filterRegistrationBean.addUrlPatterns(filter.urlPatterns());
            filterRegistrationBean.setOrder(filter.order());
            return filterRegistrationBean;
        }).collect(Collectors.toList());

    }

}

