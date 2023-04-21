package cn.mina.boot.web.common.context;


import cn.mina.boot.common.exception.ErrorCode;
import cn.mina.boot.web.common.exception.GlobalErrorCode;
import cn.mina.boot.web.common.exception.MinaGlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统工具类
 *
 * @author Created by haoteng on 2018/6/28.
 */

public class MinaWebTools {

    private static final Logger logger = LoggerFactory.getLogger(MinaWebTools.class);


    public static class context {


        private static Class<? extends MinaWebContext> customContextClazz;

        public static Class<? extends MinaWebContext> getCustomContextClass() {
            return customContextClazz;
        }

        public static <T extends MinaWebContext> T getContext(Class<T> clazz) {

            return getContextByClazz(clazz);

        }


        /**
         * 获取自定义上下文-全局配置方式
         *
         * @param <T>
         * @return
         */

        public static <T extends MinaWebContext> T getContext() {

            if (customContextClazz != null) {

                return (T) getContextByClazz(customContextClazz);
            } else {
                logger.error("未设置全局自定义上下文。");
                throw new MinaGlobalException(GlobalErrorCode.ERROR_SYS_ERROR);
            }

        }

        /**
         * 获取自定义上下文
         *
         * @param <T>
         * @return
         */
        public static <T extends MinaWebContext> T getContextByClazz(Class<T> clazz) {
            if (MinaWebContext.class.isAssignableFrom(clazz)) {
                return (T) context.contextThreadLocal.get();
            } else {
                logger.error("错误的自定义上下文类型");
                throw new MinaGlobalException(GlobalErrorCode.ERROR_SYS_ERROR);
            }
        }


        private static ThreadLocal<MinaWebContext> contextThreadLocal = new ThreadLocal<>();

        protected static void removeContext() {
            contextThreadLocal.remove();
        }


        protected static void addContext(MinaWebContext context) {
            contextThreadLocal.set(context);
        }


        /**
         * 初始化用户自定义上下文，全局只能调用一次
         *
         * @param clazz
         */
        protected synchronized static void initCustomContext(Class<? extends MinaWebContext> clazz) {
            if (customContextClazz != null) {
                logger.error("自定义上下文已设置，不能重复配置。");
                throw new MinaGlobalException(GlobalErrorCode.ERROR_SYS_ERROR);
            } else {
                customContextClazz = clazz;
            }
        }
    }


    public static class response {
        /**
         * 统一返回包装
         */
        public static <T> MinaWebResult<T> success(T t) {

            MinaWebResult<T> minaWebResult = new MinaWebResult();
            minaWebResult.setCode(0000);
            minaWebResult.setMessage("请求成功");
            minaWebResult.setData(t);
            return minaWebResult;
        }

        public static MinaWebResult success() {
            MinaWebResult minaWebResult = new MinaWebResult();
            minaWebResult.setCode(0000);
            minaWebResult.setMessage("请求成功");
            return minaWebResult;
        }


        public static MinaWebResult about() {
            MinaWebResult minaWebResult = new MinaWebResult();
            // 订单模块常规异常码
            minaWebResult.setCode(9999);
            minaWebResult.setMessage("请求失败");
            return minaWebResult;
        }

        public static MinaWebResult about(String errorMessage) {
            MinaWebResult minaWebResult = new MinaWebResult();
            // 订单模块常规异常码
            minaWebResult.setCode(9999);
            minaWebResult.setMessage(errorMessage);
            return minaWebResult;
        }

        public static MinaWebResult about(ErrorCode errorCode) {
            MinaWebResult minaWebResult = new MinaWebResult();
            minaWebResult.setCode(errorCode.getCode());
            minaWebResult.setMessage(errorCode.getMessage());
            return minaWebResult;
        }
    }


}
