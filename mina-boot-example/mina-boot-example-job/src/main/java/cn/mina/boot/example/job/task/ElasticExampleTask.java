package cn.mina.boot.example.job.task;

import cn.mina.boot.job.elastic.anno.ElasticSimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.stereotype.Component;

/**
 * @author Created by haoteng on 2023/5/6.
 */
@ElasticSimpleJob(cron = "0 * * * * ?", jobName = "mina-boot-job-test", description = "mina boot 框架job测试")
@Component
@Slf4j
public class ElasticExampleTask implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("elastic job .....");
    }
}

