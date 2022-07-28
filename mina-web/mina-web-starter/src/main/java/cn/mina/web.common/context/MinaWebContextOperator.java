package cn.mina.web.common.context;

/**
 * web context 操作类，
 *
 * @author Created by haoteng on 2022/7/20.
 */
public class MinaWebContextOperator {

    /**
     * 删除当前线程绑定的上下文
     */
    public static void removeContext() {
        MinaWebTools.context.removeContext();
    }


    /**
     * 将自定义上下文 添加到当前线程
     *
     * @param context
     */
    public static void addContext(MinaWebContext context) {
        MinaWebTools.context.addContext(context);
    }

    /**
     * 初始化用户自定义上下文，全局只能调用一次
     *
     * @param clazz
     */
    public static void initCustomContext(Class<? extends MinaWebContext> clazz) {
        MinaWebTools.context.initCustomContext(clazz);
    }
}
