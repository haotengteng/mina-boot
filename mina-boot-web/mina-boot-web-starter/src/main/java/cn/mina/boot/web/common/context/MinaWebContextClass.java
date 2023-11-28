package cn.mina.boot.web.common.context;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义系统自定义上下文注解
 *
 * @author Created by haoteng on 2023/4/21.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinaWebContextClass {
}
