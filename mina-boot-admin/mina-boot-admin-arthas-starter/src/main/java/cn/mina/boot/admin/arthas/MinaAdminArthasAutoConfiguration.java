package cn.mina.boot.admin.arthas;

import cn.mina.boot.context.property.YmlPropertySourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * @author Created by haoteng on 2023/2/17.
 */
@Configuration
@PropertySource(value = "classpath:mina-boot-admin-arthas.yml", factory = YmlPropertySourceFactory.class)
@Slf4j
public class MinaAdminArthasAutoConfiguration implements EnvironmentAware {

    private Environment environment;

    /**
     * 打印arthas agentId；
     * web项目也可以通过访问 mina-actuator/arthas 访问点获取
     */
    @PostConstruct
    public void logArthasAgentId() {
        log.info("arthas agnetId is :{}", environment.getProperty("arthas.agent-id"));
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
