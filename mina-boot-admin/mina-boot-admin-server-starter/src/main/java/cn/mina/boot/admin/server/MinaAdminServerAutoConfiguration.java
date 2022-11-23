package cn.mina.boot.admin.server;

import cn.mina.boot.context.property.YmlPropertySourceFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * kafka 消息队列自动配置类
 *
 * @author Created by haoteng on 2022/11/17.
 */
@Configuration
@PropertySource(value = "classpath:mina-boot-admin-server.yml", factory = YmlPropertySourceFactory.class)
public class MinaAdminServerAutoConfiguration {

}

