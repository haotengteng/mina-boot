package cn.mina.boot.common.exception;

/**
 * @author Created by haoteng on 2022/7/19.
 */
public class MinaBaseException extends RuntimeException {
    private final Integer code;
    private final String message;

    public MinaBaseException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public MinaBaseException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public MinaBaseException(String message) {
        this.code = ErrorCode.BASE_ERROR_CODE;
        this.message = message;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
