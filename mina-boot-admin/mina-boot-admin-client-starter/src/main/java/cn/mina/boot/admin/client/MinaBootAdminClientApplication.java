package cn.mina.boot.admin.client;

import cn.mina.boot.web.common.MinaBootWebApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinaBootAdminClientApplication {
    private static final Logger logger = LoggerFactory.getLogger(MinaBootAdminClientApplication.class);

    public static void main(String[] args) {
        MinaBootWebApplication.run(MinaBootAdminClientApplication.class, args, null);
    }
}
