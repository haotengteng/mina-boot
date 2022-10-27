package cn.mina.boot.job.xxl;

import cn.mina.boot.job.xxl.register.XxlJobAutoRegister;
import cn.mina.boot.job.xxl.register.service.JobGroupService;
import cn.mina.boot.job.xxl.register.service.JobInfoService;
import cn.mina.boot.job.xxl.register.service.JobLoginService;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Created by haoteng on 2022/10/22.
 */
@Configuration
@EnableConfigurationProperties(MinaJobXxlProperties.class)
@Import({XxlJobAutoRegister.class, JobGroupService.class, JobInfoService.class, JobLoginService.class})
@ConditionalOnProperty(prefix = "mina.job.xxl", name = "enable", havingValue = "true", matchIfMissing = true)
public class MInaJobXxlAutoconfiguration {
    @Autowired
    private MinaJobXxlProperties xxlJobProperties;

    @Bean
    public XxlJobSpringExecutor xxlJobSpringExecutor() {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdminAddresses());
        xxlJobSpringExecutor.setAppname(xxlJobProperties.getAppname());
        xxlJobSpringExecutor.setIp(xxlJobProperties.getIp());
        xxlJobSpringExecutor.setPort(xxlJobProperties.getPort());
        xxlJobSpringExecutor.setAccessToken(xxlJobProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(xxlJobProperties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobProperties.getLogRetentionDays());
        return xxlJobSpringExecutor;
    }
}
