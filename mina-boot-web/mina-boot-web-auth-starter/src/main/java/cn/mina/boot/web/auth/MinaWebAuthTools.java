package cn.mina.boot.web.auth;

import cn.mina.boot.web.common.context.MinaWebContext;

/**
 * @author Created by haoteng on 2022/7/28.
 */
public class MinaWebAuthTools {
    public static class token {

        public static TokenGenerator<MinaWebContext, String> generator;


        public static <T extends MinaWebContext> String generate(T t) {
            return generator.generate(t);
        }

    }
}
