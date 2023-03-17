package cn.mina.boot.web.common.exception;

import cn.mina.boot.web.common.context.MinaWebResult;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Created by haoteng on 2023/3/7.
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class MinaBaseErrorController extends BasicErrorController {
    public MinaBaseErrorController(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
    }

    @RequestMapping
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(status);
        }
        Map<String, Object> body = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.ALL));
        MinaWebResult<Map<String, Object>> result = new MinaWebResult();
        result.setCode((Integer) body.get("status"));
        result.setMessage("接口调用异常");
        result.setData(body);
        return new ResponseEntity<>(body, status);
    }

}
