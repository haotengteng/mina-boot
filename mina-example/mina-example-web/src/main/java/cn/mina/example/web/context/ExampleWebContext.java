package cn.mina.example.web.context;

import cn.mina.web.context.MinaWebContext;
import lombok.Data;

/**
 * @author Created by haoteng on 2022/7/20.
 */
@Data
public class ExampleWebContext extends MinaWebContext {

    private String name;
}
