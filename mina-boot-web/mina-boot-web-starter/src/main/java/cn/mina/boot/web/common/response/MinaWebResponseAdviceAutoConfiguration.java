package cn.mina.boot.web.common.response;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Created by haoteng on 2022/7/19.
 */
@Configuration
@ConditionalOnWebApplication
@Import(MinaResponseBodyAdvice.class)
public class MinaWebResponseAdviceAutoConfiguration {

}
