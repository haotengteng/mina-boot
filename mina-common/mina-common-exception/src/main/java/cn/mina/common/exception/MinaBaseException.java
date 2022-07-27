package cn.mina.common.exception;

/**
 * @author Created by haoteng on 2022/7/19.
 */
public class MinaBaseException extends RuntimeException {
    private Integer code;
    private String message;

    public MinaBaseException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public MinaBaseException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
