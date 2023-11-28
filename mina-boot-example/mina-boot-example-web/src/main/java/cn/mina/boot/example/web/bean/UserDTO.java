package cn.mina.boot.example.web.bean;

import cn.mina.boot.common.util.desensitization.Desensitization;
import cn.mina.boot.common.util.desensitization.DesensitizationEnum;
import lombok.Data;

/**
 * 脱敏功能 示例
 *
 * @author Created by haoteng on 2023/8/9.
 */
@Data
public class UserDTO {

    private Integer id;

    @Desensitization(type = DesensitizationEnum.REAL_NAME)
    private String name;

    @Desensitization(type = DesensitizationEnum.ADDRESS)
    private String address;

    private Integer age;

    @Desensitization(type = DesensitizationEnum.PHONE)
    private String phone;

    // {"id":11,"name":"郝**","address":"浙江省******","age":null,"phone":"187****6323"}
}
