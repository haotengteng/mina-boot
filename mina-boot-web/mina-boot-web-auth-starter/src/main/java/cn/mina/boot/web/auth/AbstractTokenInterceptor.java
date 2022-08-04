package cn.mina.boot.web.auth;

import cn.mina.boot.common.exception.GlobalErrorCode;
import cn.mina.boot.common.exception.MinaGlobalException;

import cn.mina.boot.web.common.context.MinaWebContext;
import cn.mina.boot.web.common.context.MinaWebContextOperator;
import cn.mina.boot.web.common.context.MinaWebTools;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author Created by haoteng on 2022/7/21.
 */
public abstract class AbstractTokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
//            throw new MinaGlobalException(GlobalErrorCode.ERROR_NO_LOGIN);
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        Login loginAnnotation = method.getAnnotation(Login.class);
        if (loginAnnotation != null) {
            String token = request.getHeader("token");
            if (isAccess(token)) {
                addContext(token, MinaWebTools.context.getCustomContextClass());
                return true;
            } else {
                throw new MinaGlobalException(GlobalErrorCode.ERROR_NO_LOGIN);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        MinaWebContextOperator.removeContext();
    }

    /**
     * 校验token是否合法
     *
     * @param token
     * @return
     */
    protected abstract boolean isAccess(String token);

    /**
     * 校验token是否合法后，将token 承载信息，设置到context上下文
     *
     * @param clazz
     */
    protected abstract void addContext(String token, Class<? extends MinaWebContext> clazz);



}
