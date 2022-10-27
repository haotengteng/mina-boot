package cn.mina.boot.web.common.exception;

import cn.mina.boot.common.exception.ErrorCode;
import cn.mina.boot.common.exception.MinaBaseException;

/**
 * @author Created by haoteng on 2022/7/19.
 */
public class MinaGlobalException extends MinaBaseException {

    public MinaGlobalException() {
        super(GlobalErrorCode.ERROR_SYS_ERROR.getCode(), GlobalErrorCode.ERROR_SYS_ERROR.getMessage());
    }

    public MinaGlobalException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MinaGlobalException(String errorMessage) {
        super(errorMessage);
    }

    public MinaGlobalException(Integer errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }


}
