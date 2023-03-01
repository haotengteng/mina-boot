package cn.mina.boot.message.rocketmq;

import cn.mina.boot.common.exception.MinaBaseException;

/**
 * @author Created by haoteng on 2023/3/1.
 */
public class MinaRocketMQConfig {

    /**
     * RocketMQTemplate 异常全局处理，mina.message.rocketmq.template.enhance = true 时启用
     */
    private static SendErrorFun sendErrorFun = null;
    private static Boolean inited = false;

    public static void setGlobalSendErrorFun(SendErrorFun sendErrorFun) {
        synchronized (inited) {
            if (inited) {
                throw new MinaBaseException("请勿重复初始化RocketMQTemplate的异常全局处理逻辑");
            } else {
                MinaRocketMQConfig.sendErrorFun = sendErrorFun;
                inited = true;
            }
        }
    }

    public static SendErrorFun getGlobalSendErrorFun() {
        return sendErrorFun;
    }
}
