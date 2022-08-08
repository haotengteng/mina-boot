//package cn.mina.boot.example.web.data.jpa.controller;
//
//import cn.mina.boot.example.web.data.jpa.dao.ExampleJpaMapper;
//import cn.mina.boot.example.web.data.jpa.entity.ExampleJpaDO;
//import cn.mina.boot.web.common.context.ClientResult;
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
//public class FooJpaController {
//
//    @Autowired
//    private ExampleJpaMapper exampleJpaMapper;
//
//    @GetMapping(path = "jpa")
//    public ClientResult<ExampleJpaDO> sayHello() throws UnknownHostException {
//        ExampleJpaDO aDo = exampleJpaMapper.findById(1).orElse(new ExampleJpaDO());
//        return MinaWebTools.response.success(aDo);
//    }
//
////    /**
////     * 分页查询
////     * @return
////     * @throws UnknownHostException
////     */
////    @GetMapping(path = "jpa/page")
////    public ClientResult<List<ExampleJpaDO>> sayHelloPage() throws UnknownHostException {
////        Page<ExampleJpaDO> page = MinaPage.cover(exampleMapper.findListByPage(1, 1));
////        return MinaWebTools.response.success(page);
////    }
//}
