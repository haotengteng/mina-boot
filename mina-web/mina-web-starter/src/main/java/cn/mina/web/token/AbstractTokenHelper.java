package cn.mina.web.token;

import cn.mina.web.context.MinaWebContext;

/**
 * @author Created by haoteng on 2022/7/20.
 */
public abstract class AbstractTokenHelper {

    public abstract <T extends MinaWebContext> String encode(T context, Integer tokenExpireTime, String secret);
}
