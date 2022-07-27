package cn.mina.web.token.jwt;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTException;
import cn.mina.common.exception.GlobalErrorCode;
import cn.mina.common.exception.MinaGlobalException;
import cn.mina.web.context.MinaWebContext;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.util.Date;

/**
 * @author Created by haoteng on 2022/7/20.
 */
public class JwtTokenHelper {

    public static String encode(MinaWebContext context, Integer tokenExpireTime, String secret) {
        return JWT.create()
                .setPayload("context", JSON.toJSONString(context))
                .setPayload("expire", DateUtil.format(DateUtil.offset(DateUtil.date(), DateField.HOUR, tokenExpireTime), "yyyy-MM-dd HH:mm:ss"))
                .setKey(secret.getBytes())
                .sign();
    }

    public static <T extends MinaWebContext> T decode(String token, Class<T> clazz) {
        JWT jwt = JWT.of(token);
        String context = (String) jwt.getPayload("context");
        return JSONObject.parseObject(context, clazz);
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
