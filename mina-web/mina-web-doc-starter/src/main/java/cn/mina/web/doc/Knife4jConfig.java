package cn.mina.web.doc;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Created by haoteng on 2021/6/26.
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class Knife4jConfig implements WebMvcConfigurer {

    @Value("${mina.web.log.groupName}")
    private String groupName;

    @Bean
    public Docket createRestApi() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("web接口文档")
                .description("mina框架集成knife4j提供web文档")
                .contact(new Contact("mina", "", ""))
                .version("1.0")
                .build();

        return new Docket(DocumentationType.OAS_30)
                .host("http://127.0.0.1/")
                .groupName(getGroupName())
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn"))
                .paths(PathSelectors.any())
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 这个是knife4j的ui
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
