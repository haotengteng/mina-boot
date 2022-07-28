package cn.mina.web.auth;

/**
 * @author Created by haoteng on 2022/7/28.
 */
@FunctionalInterface
public interface TokenGenerator<T, V> {

    public V generate(T t);
}
