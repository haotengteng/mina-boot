package cn.mina.boot.web.auth;

import cn.mina.boot.web.common.context.MinaWebContext;

/**
 * @author Created by haoteng on 2022/7/20.
 */
public abstract class AbstractTokenHelper {

    public abstract <T extends MinaWebContext> String encode(T context, Integer tokenExpireTime, String secret);
}
