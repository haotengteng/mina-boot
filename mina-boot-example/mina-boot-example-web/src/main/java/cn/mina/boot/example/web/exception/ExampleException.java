package cn.mina.boot.example.web.exception;

import cn.mina.boot.common.exception.ErrorCode;
import cn.mina.boot.common.exception.MinaBaseException;

/**
 * @author Created by haoteng on 2022/7/19.
 */
public class ExampleException extends MinaBaseException {
    public ExampleException(Integer code, String message) {
        super(code, message);
    }

    public ExampleException(ErrorCode errorCode) {
        super(errorCode);
    }
}
