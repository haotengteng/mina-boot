package cn.mina.boot.admin.client.actuator;

import cn.mina.boot.admin.client.actuator.dependency.Collector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Created by haoteng on 2023/7/20.
 */
@Configuration
public class MinaBootActuatorAutoConfiguration {


    /**
     * 进在指定的profile 执行
     * @return
     */
    @Bean
//    @ConditionalOnExpression("T(org.apache.commons.lang3.StringUtils).isNoneBlank('${mina.boot.actuator.dependency.address}')")
    public Collector minaDependencyReporter() {
        return new Collector();
    }
}
