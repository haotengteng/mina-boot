package cn.mina.boot.example.web.controller;

import cn.mina.boot.cache.redis.MinaCacheRedisUtil;
import cn.mina.boot.common.util.JsonUtil;
import cn.mina.boot.example.web.bean.UserDTO;
import cn.mina.boot.example.web.context.ExampleWebContext;
import cn.mina.boot.example.web.exception.ExampleErrorCode;
import cn.mina.boot.example.web.exception.ExampleException;
import cn.mina.boot.web.auth.Login;
import cn.mina.boot.web.auth.MinaWebAuthTools;
import cn.mina.boot.web.common.context.MinaWebContextOperator;
import cn.mina.boot.web.common.context.MinaWebResult;
import cn.mina.boot.web.common.context.MinaWebTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/foo1")
@Slf4j
public class Foo1Controller {

    @GetMapping(path = "hello")
    public String sayHello() throws UnknownHostException {
        String ip = getLocalIP();
        return ip + ": Hello docker!";
    }

    @GetMapping(path = "exception")
    public String testException() {

        throw new ExampleException(ExampleErrorCode.ERROR_INTERNAL_ERROR);
    }

    @GetMapping(path = "hello/client")
    public MinaWebResult<String> sayHelloClientResult() throws UnknownHostException {
        log.info("Hello docker !!!!");
        String ip = getLocalIP();
        return MinaWebTools.response.success(ip + ": Hello docker!!");
    }


    @GetMapping(path = "hello/context")
    public MinaWebResult sayHelloContext() {
        ExampleWebContext context = new ExampleWebContext();
        context.setName("example");
        MinaWebContextOperator.addContext(context);
        log.info("上下文内容:{}", MinaWebTools.context.getContext(ExampleWebContext.class).getName());
        MinaWebContextOperator.removeContext();
        return MinaWebTools.response.success();
    }

    @GetMapping(path = "hello/login")
    @Login
    public MinaWebResult testLoginToken() {
        ExampleWebContext context = new ExampleWebContext();
        context.setName("example");


        log.info("上下文内容:{}", MinaWebTools.context.getContext(ExampleWebContext.class).getName());
        MinaWebContextOperator.removeContext();
        return MinaWebTools.response.success();
    }

    @GetMapping(path = "hello/token/generate")
    public MinaWebResult testTokenGenerate() {
        ExampleWebContext context = new ExampleWebContext();
        context.setName("example");
        String generate = MinaWebAuthTools.token.generate(context);
        System.out.println("generate:" + generate);
        return MinaWebTools.response.success(generate);
    }

    @GetMapping(path = "hello/cache/redis")
    public MinaWebResult testCacheRedis() {
        MinaCacheRedisUtil.put("mina","a simple 框架");
        log.info("redis数据:{}",MinaCacheRedisUtil.getBean("mina",String.class));
        return MinaWebTools.response.success(MinaCacheRedisUtil.getBean("mina",String.class));
    }




    private String getLocalIP() throws UnknownHostException {
        InetAddress addr = InetAddress.getLocalHost();
        return addr.getHostName() + "-" + addr.getHostAddress();
    }




    public static void main(String[] args) {

//        testJetToken();

        UserDTO userDTO = new UserDTO();
        userDTO.setId(11);
        userDTO.setName("郝腾腾");
        userDTO.setPhone("18767176323");
        userDTO.setAddress("浙江省宁波市鄞州区下应街道东兴社区53幢304室");
        System.out.println(JsonUtil.toJSONString(userDTO));
    }

    private static void testJetToken() {
        ExampleWebContext context = new ExampleWebContext();
        context.setName("example");
        String generate = MinaWebAuthTools.token.generate(context);
        System.out.println("generate:" + generate);
//        String encode = JwtTokenHelper.encode(context,1,"");
//        System.out.println("encode:" + encode);
//        Boolean verify = JwtTokenHelper.verify(encode, JwtTokenHelper.SECRET);
//        System.out.println("verify:" + verify);
//        ExampleWebContext decode = JwtTokenHelper.decode(encode, ExampleWebContext.class);
//        System.out.println("decode:" + JSON.toJSONString(decode));
    }
}
