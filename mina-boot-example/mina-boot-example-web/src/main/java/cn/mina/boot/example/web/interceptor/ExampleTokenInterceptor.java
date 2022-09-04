package cn.mina.boot.example.web.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Created by haoteng on 2022/7/21.
 */
@Component
public  class ExampleTokenInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("-------------------");
//        return forward(request, response);
        return true;
    }

    private static boolean forward(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (request.getRequestURI().equals("/foo/hello/client")){
                return true;
            }
            request.getRequestDispatcher("/foo/hello/client").forward(request, response);
            return false;
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("====================");
    }


}
