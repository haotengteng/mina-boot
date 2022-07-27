package cn.mina.web.token.jwt;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.mina.web.context.MinaWebContext;
import cn.mina.web.context.MinaWebContextOperator;
import cn.mina.web.token.AbstractTokenInterceptor;
import cn.mina.web.token.MinaTokenProperties;
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
        if (StrUtil.isNotBlank(token) && JwtTokenHelper.verify(token, properties.getSecret())) {
            return DateUtil.compare(JwtTokenHelper.getExpire(token), new Date()) > 0;
        }
        return false;
    }

    @Override
    protected void addContext(String token, Class<? extends MinaWebContext> clazz) {
        MinaWebContext context = JwtTokenHelper.decode(token, MinaWebContext.class);
        MinaWebContextOperator.addContext(clazz.cast(context));
    }

}
