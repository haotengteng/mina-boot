package cn.mina.boot.job.xxl.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Created by haoteng on 2022/10/26.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JobAutoRegister {
    String cron();

    String jobDesc() default "自定义任务";

    String author() default "mina-job-xxl-starter";

    /*
     * 默认为 ROUND 轮询方式
     * 可选： FIRST 第一个
     * */
    String executorRouteStrategy() default "ROUND";

    int triggerStatus() default 0;
}
