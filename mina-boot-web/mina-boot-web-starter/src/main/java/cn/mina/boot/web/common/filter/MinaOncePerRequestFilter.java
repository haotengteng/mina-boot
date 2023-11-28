package cn.mina.boot.web.common.filter;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author Created by haoteng on 2023/8/17.
 */
public abstract class MinaOncePerRequestFilter extends OncePerRequestFilter {


    /**
     * 设置过滤路径
     *
     * @return
     */
    public abstract String urlPatterns();


    /**
     * 设置优先级
     *
     * @return
     */
    public abstract Integer order();

}
