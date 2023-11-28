package cn.mina.boot.common.util.desensitization;

import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

/**
 * 序列化脱敏枚举
 *
 * @author Created by haoteng on 2023/8/9.
 */
public enum DesensitizationEnum {
    /**
     * 真实姓名
     */
    REAL_NAME(val -> val.replaceAll("(.).*", "$1**")),
    /**
     * 住址
     */
    ADDRESS(val -> StringUtils.left(val, 3).concat("******")),
    /**
     * 手机号
     */
    PHONE(val -> val.replaceAll("(\\d{3})\\d+(\\d{4})", "$1****$2"));

    private final Function<String, String> function;

    DesensitizationEnum(Function<String, String> function) {
        this.function = function;
    }

    public Function<String, String> getFunction() {
        return function;
    }

}
