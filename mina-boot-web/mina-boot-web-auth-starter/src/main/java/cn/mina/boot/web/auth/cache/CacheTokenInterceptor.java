package cn.mina.boot.web.auth.cache;

import cn.mina.boot.cache.redis.MinaCacheRedisUtil;
import cn.mina.boot.web.auth.AbstractTokenInterceptor;
import cn.mina.boot.web.auth.MinaTokenProperties;
import cn.mina.boot.web.common.context.MinaWebContext;
import cn.mina.boot.web.common.context.MinaWebContextOperator;
import lombok.extern.slf4j.Slf4j;

/**
 * token 拦截器缓存实现
 *
 * @author Created by haoteng on 2021/6/22.
 */
@Slf4j
public class CacheTokenInterceptor extends AbstractTokenInterceptor {

    private final MinaTokenProperties properties;

    public CacheTokenInterceptor(MinaTokenProperties minaTokenProperties) {
        this.properties = minaTokenProperties;
    }

    @Override
    protected boolean isAccess(String token) {
        return MinaCacheRedisUtil.hasKey(token);
    }

    @Override
    protected void addContext(String token, Class<? extends MinaWebContext> clazz) {
        MinaWebContextOperator.addContext(MinaCacheRedisUtil.getBean(token, clazz));
    }
}
