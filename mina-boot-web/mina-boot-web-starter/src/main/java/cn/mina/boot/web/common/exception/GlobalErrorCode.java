package cn.mina.boot.web.common.exception;

import cn.mina.boot.common.exception.ErrorCode;

/**
 * @author Created by haoteng on 2022/7/19.
 */
public enum GlobalErrorCode implements ErrorCode {
    ERROR_SYS_ERROR(100100, "系统错误"),

    ERROR_NOT_FOUND(100101, "请求地址不存在"),

    ERROR_ILLEGAL_PARAMETER(100102, "缺少参数或参数不合法"),

    ERROR_SERVER_MAINTENANCE(100103, "服务器正在维护。如有问题请联系客服"),

    ERROR_INTERNAL_ERROR(100104, "您的操作有误或服务器繁忙，请稍后重试。如有问题请联系客服"),

    ERROR_ATTACK_BLOCKED(100105, "客户端禁用，攻击机器"),

    ERROR_INJECT_REQUEST(100106, "请求不合法，注入攻击请求"),

    ERROR_NO_LOGIN(100107, "请重新登录"),

    ERROR_NO_AUTHORITY(100108, "您的账号暂无权限"),

    ERROR_REPEAT_REQUEST(100109, "请求重复"),

    ERROR_NO_DATA(100110, "没有数据"),

    ERROR_ILLEGAL_CONFIG(100112, "缺少必要配置信息或配置信息有误"),

    ERROR_ILLEGAL_PAGE_PARAMETER(100113, "缺少必要的分页参数参数"),
    ;

    private int code;

    private String message;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    GlobalErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public ErrorCode customCode(int code) {
        this.code = code;
        return this;
    }

    public ErrorCode customMassage(String message) {
        this.message = message;
        return this;
    }

    public ErrorCode customCodeAndMassage(int code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }
}
