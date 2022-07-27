package cn.mina.web.doc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Created by haoteng on 2022/7/27.
 */
@Configuration
@ConditionalOnWebApplication
@ConditionalOnProperty(prefix = "mina.web.doc", name = "enable", havingValue = "true")
@Import(Knife4jConfig.class)
public class MinaWebDocAutoConfiguration {


}
