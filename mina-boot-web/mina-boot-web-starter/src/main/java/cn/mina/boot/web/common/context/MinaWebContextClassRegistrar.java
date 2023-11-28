package cn.mina.boot.web.common.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Created by haoteng on 2023/4/19.
 */
@Slf4j
public class MinaWebContextClassRegistrar implements ImportBeanDefinitionRegistrar {

    private static final String RESOURCE_PATTERN = "/**/*.class";

    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {

        AnnotationAttributes attributes = AnnotationAttributes
                .fromMap(metadata.getAnnotationAttributes(AutoConfigurationPackage.class.getName(), false));
        if (attributes == null) {
            return;
        }
        List<String> packageNames = new ArrayList<>(Arrays.asList(attributes.getStringArray("basePackages")));
        for (Class<?> basePackageClass : attributes.getClassArray("basePackageClasses")) {
            packageNames.add(basePackageClass.getPackage().getName());
        }
        if (packageNames.isEmpty()) {
            packageNames.add(ClassUtils.getPackageName(metadata.getClassName()));
        }
        List<String> packageList = Collections.unmodifiableList(packageNames);
        initJunWebContextClass(packageList.get(0));
    }

    public void initJunWebContextClass(String basePackage) {

        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        try {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    ClassUtils.convertClassNameToResourcePath(basePackage) + RESOURCE_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                MetadataReader reader = readerFactory.getMetadataReader(resource);
                //扫描到的class
                String classname = reader.getClassMetadata().getClassName();
                Class<?> clazz = Class.forName(classname);
                //判断是否有指定主解
                MinaWebContextClass anno = clazz.getAnnotation(MinaWebContextClass.class);
                if (anno != null) {
                    if (MinaWebContext.class.isAssignableFrom(clazz)) {
                        Class<? extends MinaWebContext> contextClass = clazz.asSubclass(MinaWebContext.class);
                        MinaWebContextOperator.initCustomContext(contextClass);
                        log.info("初始化MinaWebContext上下文类信息成功:{}", clazz.getName());
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            log.error("初始化自定义上下文类信息失败，请求上下文功能不可用", e);
        }
    }
}

