package cn.mina.boot.ratelimit.sentinel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * sentinel 限流处理接口
 * @author Created by haoteng on 2023/3/17.
 */
@FunctionalInterface
public interface FlowExceptionHandler {

    void process(HttpServletRequest request, HttpServletResponse response);
}
