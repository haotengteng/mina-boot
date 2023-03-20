package cn.mina.boot.ratelimit.sentinel;

import cn.mina.boot.ratelimit.sentinel.exception.MinaRatelimitException;
import cn.mina.boot.ratelimit.sentinel.exception.RatelimitErrorCode;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Created by haoteng on 2023/3/19.
 */
public class SentinelBlockExceptionHandler implements BlockExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(SentinelBlockExceptionHandler.class);

    @Autowired
    private FlowExceptionHandler flowExceptionHandler;
    @Autowired
    private DegradeExceptionHandler degradeExceptionHandler;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) {
        String resourceName = e.getRule().getResource();
        if (e instanceof FlowException) {
            logger.error("请求触发限流设置，该请求已拦截：{}", resourceName);
            // 触发限流抛出自定义异常
            flowExceptionHandler.process(request, response);
        } else if (e instanceof DegradeException) {
            logger.error("请求触发降级设置，该请求已降级处理：{}", resourceName);
            // 触发降级 抛出自定义异常
            degradeExceptionHandler.process(request, response);
        } else {
            logger.error("请求触发sentinel拦截，该请求拦截：{}", resourceName, e);
            //  兜底异常处理
            throw new MinaRatelimitException(RatelimitErrorCode.ERROR_RATELIMIT_BLOCK_ERROR);
        }
    }
}
