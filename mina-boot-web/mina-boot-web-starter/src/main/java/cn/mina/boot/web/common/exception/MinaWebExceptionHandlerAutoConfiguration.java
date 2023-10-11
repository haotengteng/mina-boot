package cn.mina.boot.web.common.exception;

import cn.mina.boot.support.YmlPropertySourceFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by haoteng on 2022/7/19.
 */
@AutoConfigureBefore(ErrorMvcAutoConfiguration.class)
@ConditionalOnWebApplication
@EnableConfigurationProperties({ServerProperties.class, WebMvcProperties.class})
@PropertySource(value = "classpath:mina-boot-web.yml",factory = YmlPropertySourceFactory.class)
public class MinaWebExceptionHandlerAutoConfiguration {


    @Bean
    public WebExceptionHandler webExceptionHandler() {
        return new WebExceptionHandler();
    }


    @Bean
    @ConditionalOnMissingBean(value = ErrorController.class, search = SearchStrategy.CURRENT)
    public MinaBaseErrorController basicErrorController(ErrorAttributes errorAttributes,
                                                        ServerProperties serverProperties,
                                                        ObjectProvider<List<ErrorViewResolver>> errorViewResolversProvider) {

        return new MinaBaseErrorController(errorAttributes, serverProperties.getError(),
                errorViewResolversProvider.getIfAvailable());
    }

}
