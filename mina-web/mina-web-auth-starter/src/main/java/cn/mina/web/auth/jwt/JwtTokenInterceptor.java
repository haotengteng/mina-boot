package cn.mina.web.auth.jwt;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.mina.web.common.context.MinaWebContext;
import cn.mina.web.common.context.MinaWebContextOperator;
import cn.mina.web.auth.AbstractTokenInterceptor;
import cn.mina.web.auth.MinaTokenProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * token 拦截器jwt实现
 *
 * @author Created by haoteng on 2021/6/22.
 */
@Slf4j
public class JwtTokenInterceptor extends AbstractTokenInterceptor {

    private MinaTokenProperties properties;

    public JwtTokenInterceptor(MinaTokenProperties minaTokenProperties) {
        this.properties = minaTokenProperties;
    }

    @Override
    protected boolean isAccess(String token) {
        if (StrUtil.isNotBlank(token) && JwtTokenUtil.verify(token, properties.getSecret())) {
            return DateUtil.compare(JwtTokenUtil.getExpire(token), new Date()) > 0;
        }
        return false;
    }

    @Override
    protected void addContext(String token, Class<? extends MinaWebContext> clazz) {
        MinaWebContext context = JwtTokenUtil.decode(token, MinaWebContext.class);
        MinaWebContextOperator.addContext(clazz.cast(context));
    }

}
