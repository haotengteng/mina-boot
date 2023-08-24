package cn.mina.boot.web.common.exception;

import cn.mina.boot.common.exception.MinaBaseException;
import cn.mina.boot.common.util.JsonUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
        ObjectNode result = JsonUtils.getObjectNode();
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
        response.getWriter().append(JsonUtils.toJSONString(result)).flush();
        return new ModelAndView();
    }
}
