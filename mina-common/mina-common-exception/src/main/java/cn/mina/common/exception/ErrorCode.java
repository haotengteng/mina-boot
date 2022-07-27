package cn.mina.common.exception;

/**
 * @author Created by haoteng on 2022/7/19.
 * 自定义枚举继承该接口，添加一下两个属性
 * private int code;
 * private String message;
 * <p>
 * ERROR_SYS_CUSTOM(601000100, "系统自定义错误码")
 * <p>
 * 可选实现：修改已定义枚举的错误code和错误信息
 * customCode(int code)
 * customMassage(String message)
 * customCodeAndMassage(int code, String message)
 */
public interface ErrorCode {


    int getCode();


    String getMessage();


    default ErrorCode customCode(int code) {
//        this.code = code;
        return this;
    }

    default ErrorCode customMassage(String message) {
//        this.message = message;
        return this;
    }

    default ErrorCode customCodeAndMassage(int code, String message) {
//        this.code = code;
//        this.message = message;
        return this;
    }

}
