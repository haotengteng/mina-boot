package cn.mina.boot.ratelimit.sentinel;

import cn.mina.boot.ratelimit.sentinel.exception.MinaRatelimitException;
import cn.mina.boot.ratelimit.sentinel.exception.RatelimitErrorCode;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Created by haoteng on 2023/3/15.
 */
@Configuration
@Import(MinaBootSentinelConfigurer.class)
public class MinaBootRatelimitSentinelAutoConfiguration {


    /**
     * 默认 sentinel 异常处理类（springmvc sentinel 和springcloud alibaba sentinel 共用）
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public BlockExceptionHandler sentinelBlockExceptionHandler() {
        return new SentinelBlockExceptionHandler();
    }


    /**
     * 定义 原始url到资源的转换器
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public UrlCleanerAdapter urlCleanerAdapter() {
        return originUrl -> {
            // 不希望统计 *.ico 的资源文件，可以将其转换为 empty string (since 1.6.3)
            if (originUrl.endsWith(".ico")) {
                return "";
            }
//            // 比如将满足 /foo/{id} 的 URL 都归到 /foo/*
//            if (originUrl.startsWith("/foo/")) {
//                return "/foo/*";
//            }
            return originUrl;
        };
    }

    /**
     * 触发限流 执行逻辑
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication
    public FlowExceptionHandler flowExceptionHandler() {
        return (request, response) -> {
            // 触发限流抛出自定义异常
            throw new MinaRatelimitException(RatelimitErrorCode.ERROR_RATELIMIT_BLOCK_ERROR);
        };
    }

    /**
     * 触发降级 执行逻辑
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication
    public DegradeExceptionHandler degradeExceptionHandler() {
        return (request, response) -> {
            // 触发限流抛出自定义异常
            throw new MinaRatelimitException(RatelimitErrorCode.ERROR_RATELIMIT_DEGRADE_ERROR);
        };
    }
}
