package cn.mina.boot.data.mybatis;

import cn.mina.boot.support.YmlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Created by haoteng on 2023/4/23.
 */
@Configuration
@PropertySource(value = "classpath:mina-boot-data-mybatis.yaml", factory = YmlPropertySourceFactory.class)
public class MinaDataMybatisAutoConfiguration {
}
