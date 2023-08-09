package cn.mina.boot.admin.client.actuator.dependency;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author Created by haoteng on 2023/7/20.
 */
@Slf4j
public class Collector implements CommandLineRunner {



    /**
     * 定时上报项目依赖情况
     *
     * @param args incoming main method arguments
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("mina-boot-actuator-dependency" + "-%d").build();
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, threadFactory);
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> {
            try {
                ClassPathResource classPathResource = new ClassPathResource("dependency.txt");
                InputStream inputStream = classPathResource.getInputStream();
                String dependency = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
                log.debug("mina-boot-actuator-dependency 收集信息:{}", dependency);
            } catch (Exception e) {
                log.debug("mina-boot-actuator-dependency 上报项目依赖信息失败:{}", e.getMessage());
            }

        }, 3, 6, TimeUnit.SECONDS);

    }


}
