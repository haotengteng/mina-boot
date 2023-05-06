package cn.mina.boot.job.elastic;


import cn.mina.boot.job.elastic.anno.ElasticSimpleJob;
import cn.mina.boot.support.YmlPropertySourceFactory;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.shardingsphere.elasticjob.lite.api.bootstrap.impl.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author Created by haoteng on 2023/5/6.
 */
@Configuration
@ConditionalOnProperty(
        name = {"elasticjob.enabled"},
        havingValue = "true",
        matchIfMissing = true
)
@PropertySource(value = "classpath:mina-boot-job-elastic.yml", factory = YmlPropertySourceFactory.class)
public class MinaJobElasticAutoConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ZookeeperRegistryCenter regCenter;

    @PostConstruct
    public void initElasticJob() {
        Map<String, SimpleJob> map = applicationContext.getBeansOfType(SimpleJob.class);
        for (Map.Entry<String, SimpleJob> entry : map.entrySet()) {
            SimpleJob simpleJob = entry.getValue();
            ElasticSimpleJob annotation = simpleJob.getClass().getAnnotation(ElasticSimpleJob.class);
            if (StringUtils.isBlank(annotation.cron())) {
                continue;
            }
            String cron = annotation.cron();
            String jobName = StringUtils.defaultIfBlank(annotation.jobName(), simpleJob.getClass().getName());

            JobConfiguration jobConfiguration = JobConfiguration.newBuilder(jobName, annotation.shardingTotalCount())
                    .cron(cron)
                    .shardingItemParameters(annotation.shardingItemParameters())
                    .jobParameter(annotation.jobParameter())
                    .overwrite(annotation.overwrite())
                    .description(annotation.description())
                    .disabled(annotation.disabled())
                    .build();
            ScheduleJobBootstrap jobScheduler = new ScheduleJobBootstrap(regCenter, simpleJob, jobConfiguration);
            jobScheduler.schedule();
        }
    }
}
