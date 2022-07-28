package cn.mina.web.auth;

import cn.mina.web.common.context.MinaWebContext;

/**
 * @author Created by haoteng on 2022/7/20.
 */
public abstract class AbstractTokenHelper {

    public abstract <T extends MinaWebContext> String encode(T context, Integer tokenExpireTime, String secret);
}
