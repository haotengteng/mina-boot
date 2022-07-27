package cn.mina.web.token.cache;

import cn.mina.web.context.MinaWebContext;
import cn.mina.web.token.AbstractTokenInterceptor;
import cn.mina.web.token.MinaTokenProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * token 拦截器缓存实现
 *
 * @author Created by haoteng on 2021/6/22.
 */
@Slf4j
public class CacheTokenInterceptor extends AbstractTokenInterceptor {

    private MinaTokenProperties properties;

    public CacheTokenInterceptor(MinaTokenProperties minaTokenProperties) {
        this.properties = minaTokenProperties;
    }

    @Override
    protected  boolean isAccess(String token) {


        return false;
    }

    @Override
    protected void addContext(String token, Class<? extends MinaWebContext> clazz) {

    }
}
