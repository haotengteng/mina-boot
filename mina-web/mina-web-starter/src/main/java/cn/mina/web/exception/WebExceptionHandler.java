package cn.mina.web.exception;

import cn.mina.common.exception.GlobalErrorCode;
import cn.mina.common.exception.MinaBaseException;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.SneakyThrows;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Created by haoteng on 2022/7/19.
 */

public class WebExceptionHandler implements HandlerExceptionResolver {

    @SneakyThrows(IOException.class)
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        JSONObject result = new JSONObject();
        ex.printStackTrace();
        if (ex instanceof MinaBaseException) {
            MinaBaseException baseException = (MinaBaseException) ex;
            result.put("code", baseException.getCode());
            result.put("message", baseException.getMessage());
        }else {
            result.put("code", GlobalErrorCode.ERROR_SYS_ERROR.getCode());
            result.put("message", GlobalErrorCode.ERROR_SYS_ERROR.getMessage());
        }
        response.addHeader("Content-Type", "application/json; charset=UTF-8");
        response.getWriter().append(JSON.toJSONString(result)).flush();
        return new ModelAndView();
    }
}
