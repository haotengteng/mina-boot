package cn.mina.boot.example.web.data.mybatis.controller;

import cn.mina.boot.data.mybatis.MinaPage;
import cn.mina.boot.data.mybatis.Page;
import cn.mina.boot.example.web.data.mybatis.dao.ExampleMapper;
import cn.mina.boot.example.web.data.mybatis.entity.ExampleDO;
import cn.mina.boot.web.common.context.ClientResult;
import cn.mina.boot.web.common.context.MinaWebTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.List;

@RestController
@RequestMapping("/foo")
@Slf4j
public class FooController {

    @Autowired
    private ExampleMapper exampleMapper;

    @GetMapping(path = "mysql")
    public ClientResult<ExampleDO> sayHello() throws UnknownHostException {
        ExampleDO aDo = exampleMapper.findById(1);
        return MinaWebTools.response.success(aDo);
    }

    /**
     * 分页查询
     * @return
     * @throws UnknownHostException
     */
    @GetMapping(path = "mysql/page")
    public ClientResult<List<ExampleDO>> sayHelloPage() throws UnknownHostException {
        Page<ExampleDO> page = MinaPage.cover(exampleMapper.findListByPage(1, 1));
        return MinaWebTools.response.success(page);
    }
}
