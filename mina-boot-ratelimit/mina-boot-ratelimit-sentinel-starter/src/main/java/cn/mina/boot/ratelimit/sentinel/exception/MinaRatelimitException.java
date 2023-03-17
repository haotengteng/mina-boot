package cn.mina.boot.ratelimit.sentinel.exception;

import cn.mina.boot.common.exception.ErrorCode;
import cn.mina.boot.common.exception.MinaBaseException;
import cn.mina.boot.web.common.exception.GlobalErrorCode;

/**
 * @author Created by haoteng on 2022/7/19.
 */
public class MinaRatelimitException extends MinaBaseException {

    public MinaRatelimitException() {
        super(GlobalErrorCode.ERROR_SYS_ERROR.getCode(), GlobalErrorCode.ERROR_SYS_ERROR.getMessage());
    }

    public MinaRatelimitException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MinaRatelimitException(String errorMessage) {
        super(errorMessage);
    }

    public MinaRatelimitException(Integer errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }


}
