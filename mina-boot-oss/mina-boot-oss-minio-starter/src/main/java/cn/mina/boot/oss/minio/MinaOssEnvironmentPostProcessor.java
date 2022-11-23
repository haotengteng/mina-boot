package cn.mina.boot.oss.minio;

import cn.mina.boot.common.exception.MinaBaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

/**
 * 加载mina-oss组件基础配置
 * 暂时使用@PropertSource 注解替换
 *
 * @author Created by haoteng on 2022/11/23.
 */
@Slf4j
@Deprecated
public class MinaOssEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final YamlPropertySourceLoader loader = new YamlPropertySourceLoader();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Resource path = new ClassPathResource("mina-oss-minio.yml");
        if (!path.exists()) {
            log.warn("not found classpath:mina-oss-minio.yml config file,framework config will not effect");
        }
        load(environment, path);
    }


    private void load(ConfigurableEnvironment environment, Resource path) {
        List<PropertySource<?>> propertySources = null;
        try {
            propertySources = loader.load("mina-oss-minio-application", path);

            for (PropertySource propertySource : propertySources) {
                environment.getPropertySources().addLast(propertySource);
                log.debug("mina-boot-oss-minio-starter has load inner config mina-oss-minio.yml");
            }
        } catch (IOException e) {
            throw new MinaBaseException("failed load mina-oss-minio.yml config");
        }
    }
}
