package cn.mina.boot.protocol.api;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 定义协议体
 * @author Created by haoteng on 2023/2/20.
 */
@Data
@Builder
public class Protocol implements Serializable {

    private static final long serialVersionUID = -3475224483670645340L;

    /**
     * 协议名
     */
    private String name;
    /**
     * 方向
     */
    private Direction direction;
    /**
     * 描述
     */
    private String desc;


}
