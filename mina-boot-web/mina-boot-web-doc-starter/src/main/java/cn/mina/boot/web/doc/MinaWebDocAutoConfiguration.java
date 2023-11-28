package cn.mina.boot.web.doc;

import cn.mina.boot.support.YmlPropertySourceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Created by haoteng on 2022/7/27.
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "mina.web.doc", name = "enable", havingValue = "true", matchIfMissing = false)
@PropertySource(value = "classpath:mina-boot-web-doc.yml", factory = YmlPropertySourceFactory.class)
@Import({Knife4jConfig.class, SpringfoxHandlerProviderBeanPostProcessor.class})
public class MinaWebDocAutoConfiguration {

}
