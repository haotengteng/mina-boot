package cn.mina.boot.common.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.ReflectUtils;
import net.sf.cglib.proxy.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理工具类
 *
 * @author Created by haoteng on 2022/11/6.
 */
public class ProxyUtil {

    public static Object proxy(Class clazz, InvocationHandler h) {
        ClassLoader classLoader = clazz.getClassLoader();
        Object proxyInstance = Proxy.newProxyInstance(classLoader, clazz.getInterfaces(), h);
        return proxyInstance;
    }

    public static Object proxy(Class clazz,Callback callback) {
        return  Enhancer.create(clazz, callback);
    }
//
//    public interface Test {
//        String say(String h);
//    }
//
//    public static class TestImpl implements Test {
//
//
//        @Override
//        public String say(String h) {
//            System.out.println(h);
//            return h;
//        }
//    }

//    public static void main(String[] args) {
//        TestImpl test = (TestImpl)ProxyUtil.proxy(TestImpl.class,new MethodInterceptor() {
//            @Override
//            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//                System.out.println(11);
//                Object result = methodProxy.invokeSuper(o, objects);
//                System.out.println(22);
//                return result;
//            }
//        });
//        test.say("he");
//
//    }

//    public static void main(String[] args) {
//        TestImpl test = new TestImpl();
//        Object proxy = ProxyUtil.proxy(test.getClass(), new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                System.out.println(111);
//                Object invoke = method.invoke(test, args);
//                System.out.println(222);
//                return invoke;
//            }
//        });
//        Test test1 = (Test) proxy;
//        test1.say("你好");
//
//    }

}
