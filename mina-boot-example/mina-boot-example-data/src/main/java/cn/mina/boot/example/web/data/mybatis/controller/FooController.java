//package cn.mina.boot.example.web.data.mybatis.controller;
//
//import cn.mina.boot.data.mybatis.MinaPage;
//import cn.mina.boot.data.mybatis.Page;
//import cn.mina.boot.example.web.data.mybatis.dao.ExampleMapper;
//import cn.mina.boot.example.web.data.mybatis.dto.QueryDto;
//import cn.mina.boot.example.web.data.mybatis.entity.ExampleDO;
//import cn.mina.boot.web.common.context.MinaWebResult;
//import cn.mina.boot.web.common.context.MinaWebTools;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.net.UnknownHostException;
//
//@RestController
//@RequestMapping("/foo")
//@Slf4j
//public class FooController {
//
//    @Autowired
//    private ExampleMapper exampleMapper;
//
//    @GetMapping(path = "mysql")
//    public MinaWebResult<ExampleDO> sayHello() throws UnknownHostException {
//        ExampleDO aDo = exampleMapper.findById(1);
//        return MinaWebTools.response.success(aDo);
//    }
//
//
//    @GetMapping(path = "mysql/dto")
//    public MinaWebResult<ExampleDO> sayHelloDto() throws UnknownHostException {
//        ExampleDO exampleDO = new ExampleDO();
//        exampleDO.setId(1);
//        exampleDO.setName("tom");
//        ExampleDO aDo = exampleMapper.findByDto(exampleDO);
//        return MinaWebTools.response.success(aDo);
//    }
//
//    /**
//     * 分页查询(以.ByPage结尾)
//     * @return
//     * @throws UnknownHostException
//     */
//    @GetMapping(path = "mysql/page")
//    public MinaWebResult<Page<ExampleDO>> sayHelloPage() throws UnknownHostException {
//        Page<ExampleDO> page = MinaPage.cover(exampleMapper.findListByPage(1, 2));
//        return MinaWebTools.response.success(page);
//    }
//
//    /**
//     * 分页查询(以.ByPage结尾)
//     * @return
//     * @throws UnknownHostException
//     */
//    @GetMapping(path = "mysql/page/dto")
//    public MinaWebResult<Page<ExampleDO>> sayPage() throws UnknownHostException {
//        QueryDto query = new QueryDto();
//        query.setPage(1);
//        query.setPageSize(2);
//        Page<ExampleDO> page = MinaPage.cover(exampleMapper.findByPage(query));
//        return MinaWebTools.response.success(page);
//    }
//}
