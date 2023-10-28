package cn.mina.boot.data.hugegraph;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Created by haoteng on 2023/10/11.
 */
@Data
@Component
@ConfigurationProperties(prefix = "mina.boot.data.hugegraph")
public class MinaDataHugeGraphProperties {

    private String url;

    private String graph;

    // 默认未开启用户权限

    private String username = "**";

    private String password = "**";
}
