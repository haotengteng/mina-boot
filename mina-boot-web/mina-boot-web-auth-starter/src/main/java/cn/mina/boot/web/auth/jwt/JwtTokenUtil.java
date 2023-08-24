package cn.mina.boot.web.auth.jwt;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.mina.boot.web.common.exception.GlobalErrorCode;
import cn.mina.boot.web.common.exception.MinaGlobalException;
import cn.mina.boot.common.util.JsonUtils;
import cn.mina.boot.web.common.context.MinaWebContext;

import java.util.Date;

/**
 * @author Created by haoteng on 2022/7/20.
 */
public class JwtTokenUtil {

    public static String encode(MinaWebContext context, Integer tokenExpireTime, String secret) {
        return JWT.create()
                .setPayload("context", JsonUtils.toJSONString(context))
                .setPayload("expire", DateUtil.format(DateUtil.offset(DateUtil.date(), DateField.HOUR, tokenExpireTime), "yyyy-MM-dd HH:mm:ss"))
                .setKey(secret.getBytes())
                .sign();
    }

    public static <T extends MinaWebContext> T decode(String token, Class<T> clazz) {
        JWT jwt = JWT.of(token);
        String context = (String) jwt.getPayload("context");
        return JsonUtils.toBean(context, clazz);
    }

    public static Date getExpire(String token) {
        JWT jwt = JWT.of(token);
        String expire = (String) jwt.getPayload("expire");
        return DateUtil.parse(expire);
    }


    public static Boolean verify(String token, String secret) {
        boolean verify;
        try {
            verify = JWT.of(token).setKey(secret.getBytes()).verify();
        } catch (JWTException e) {
            throw new MinaGlobalException(GlobalErrorCode.ERROR_NO_LOGIN);
        }
        return verify;
    }
}
