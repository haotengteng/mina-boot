package cn.mina.boot.ratelimit.sentinel;

import cn.mina.boot.ratelimit.sentinel.exception.MinaRatelimitException;
import cn.mina.boot.ratelimit.sentinel.exception.RatelimitErrorCode;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.SentinelWebInterceptor;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.config.SentinelWebMvcConfig;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreaker;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.EventObserverRegistry;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Optional;


/**
 * @author Created by haoteng on 2023/3/15.
 */
@ConditionalOnWebApplication
public class MinaBootSentinelConfigurer implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(MinaBootSentinelConfigurer.class);
    @Autowired
    private UrlCleanerAdapter urlCleanerAdapter;
    @Autowired
    private BlockExceptionHandler blockExceptionHandler;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        SentinelWebMvcConfig config = new SentinelWebMvcConfig();

        config.setBlockExceptionHandler(blockExceptionHandler);

        config.setUrlCleaner(originUrl -> {
            if (originUrl == null || originUrl.isEmpty()) {
                return originUrl;
            }
            return urlCleanerAdapter.process(originUrl);

        });
        // 设置 资源携带 方法前缀 例：GET:/foo
        config.setHttpMethodSpecify(true);
        registry.addInterceptor(new SentinelWebInterceptor(config)).addPathPatterns("/**");
    }

    static {
        EventObserverRegistry.getInstance().addStateChangeObserver("logging",
                (prevState, newState, rule, snapshotValue) -> {
                    if (newState == CircuitBreaker.State.OPEN) {
                        // 变换至 OPEN state 时会携带触发时的值
                        System.err.println(String.format("%s -> OPEN at %d, snapshotValue=%.2f", prevState.name(),
                                TimeUtil.currentTimeMillis(), snapshotValue));
                    } else {
                        System.err.println(String.format("%s -> %s at %d", prevState.name(), newState.name(),
                                TimeUtil.currentTimeMillis()));
                    }
                });
    }
}
