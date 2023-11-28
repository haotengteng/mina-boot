package cn.mina.boot.web.auth;

/**
 * @author Created by haoteng on 2022/7/28.
 */
@FunctionalInterface
public interface TokenGenerator<T, V> {

    V generate(T t);
}
