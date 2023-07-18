package cn.mina.boot.data.mybatis;

import cn.mina.boot.support.YmlPropertySourceFactory;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Created by haoteng on 2023/4/23.
 */
@AutoConfigureBefore(DruidDataSourceAutoConfigure.class)
@Configuration
@PropertySource(value = "classpath:mina-boot-data-mybatis.yml", factory = YmlPropertySourceFactory.class)
public class MinaDataMybatisAutoConfiguration {
}
