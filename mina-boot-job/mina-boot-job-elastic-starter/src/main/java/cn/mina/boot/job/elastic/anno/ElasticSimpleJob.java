package cn.mina.boot.job.elastic.anno;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author Created by haoteng on 2023/5/6.
 */


@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ElasticSimpleJob {
    @AliasFor("cron")
    String value() default "";

    @AliasFor("value")
    String cron() default "";

    String jobName() default "";

    int shardingTotalCount() default 1;

    String shardingItemParameters() default "";

    String jobParameter() default "";

    String description() default "";

    boolean disabled() default false;

    boolean overwrite() default true;
}
