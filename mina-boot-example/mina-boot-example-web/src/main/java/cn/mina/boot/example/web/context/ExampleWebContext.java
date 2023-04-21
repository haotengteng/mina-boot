package cn.mina.boot.example.web.context;

import cn.mina.boot.web.common.context.MinaWebContext;
import cn.mina.boot.web.common.context.MinaWebContextClass;
import lombok.Data;

/**
 * @author Created by haoteng on 2022/7/20.
 */
@Data
@MinaWebContextClass
public class ExampleWebContext extends MinaWebContext {

    private String name;
}
