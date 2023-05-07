package cn.mina.boot.example.config.apollo;

import cn.mina.boot.MinaApplication;
import cn.mina.boot.web.common.MinaBootApplication;
import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

/**
 * @author Created by haoteng on 2023/5/6.
 */
@MinaBootApplication
@EnableApolloConfig
public class ExampleConfigApolloApplication {
    public static void main(String[] args) {
        MinaApplication.run(ExampleConfigApolloApplication.class, args);
    }

}
