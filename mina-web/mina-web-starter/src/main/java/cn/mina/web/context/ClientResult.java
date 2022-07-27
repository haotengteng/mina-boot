package cn.mina.web.context;

import java.io.Serializable;

/**
 * @author Created by haoteng on 2022/7/19.
 */
public class ClientResult<T> implements Serializable {
    private static final long serialVersionUID = 7276088992244197729L;
    private Integer code;
    private String message;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
