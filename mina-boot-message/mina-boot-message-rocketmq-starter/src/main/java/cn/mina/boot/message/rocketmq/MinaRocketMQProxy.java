package cn.mina.boot.message.rocketmq;

import cn.mina.boot.common.util.ProxyUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

import java.lang.reflect.Method;

/**
 * @author Created by haoteng on 2023/2/28.
 */
@Slf4j
public class MinaRocketMQProxy {

    /**
     *
     * @param rocketMQTemplate
     * @param errorFun
     * @return
     */
    public static RocketMQTemplate enhance(RocketMQTemplate rocketMQTemplate, SendErrorFun errorFun) {
        // 创建增强代理类
        RocketMQTemplate proxy = (RocketMQTemplate) ProxyUtil.proxy(RocketMQTemplate.class, new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Object result = null;
                try {
                    result = methodProxy.invoke(rocketMQTemplate, objects);
                } catch (Exception e) {
                    errorFun.invoke(e);
                    log.error("rocketmq send message error ,exception:{}", e.getMessage());
                }
                return result;
            }
        });
        return proxy;
    }
}
