package cn.mina.boot.ratelimit.sentinel;

/**
 * @author Created by haoteng on 2023/3/15.
 */
@FunctionalInterface
public interface UrlCleanerAdapter {

    String process(String originUrl);
}
