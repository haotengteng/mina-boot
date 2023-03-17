package cn.mina.boot.ratelimit.sentinel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Created by haoteng on 2023/3/17.
 */
@FunctionalInterface
public interface DegradeExceptionHandler {

    void process(HttpServletRequest request, HttpServletResponse response);
}
