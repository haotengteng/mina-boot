package cn.mina.boot.web.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author Created by haoteng on 2023/8/17.
 */
public abstract class MinaHandlerInterceptor implements HandlerInterceptor {

    /**
     * 拦截路径（/**：拦截所有）
     */
    protected abstract String havePathPattern();

}
