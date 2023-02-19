package cn.mina.boot.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.Properties;

/**
 * 增强PropertySourceFactory 提供yml文件加载能力
 * @author Created by haoteng on 2022/11/23.
 */
@Slf4j
public class YmlPropertySourceFactory extends DefaultPropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        String sourceName = name != null ? name : resource.getResource().getFilename();
        if (!resource.getResource().exists()) {
            //不存在就返回一个空
            log.warn("load config file failed, not found {} config file",name);
            return new PropertiesPropertySource(sourceName, new Properties());
        }
        if (sourceName.endsWith(".yml") || sourceName.endsWith(".yaml")) {
            //yml这里手动加进去
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource.getResource());
            factory.afterPropertiesSet();
            return new PropertiesPropertySource(sourceName, factory.getObject());
        }
        return super.createPropertySource(name, resource);
    }
}
