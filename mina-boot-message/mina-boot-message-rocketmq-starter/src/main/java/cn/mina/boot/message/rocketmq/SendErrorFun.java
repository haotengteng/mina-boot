package cn.mina.boot.message.rocketmq;

/**
 * @author Created by haoteng on 2023/3/1.
 */
@FunctionalInterface
public interface SendErrorFun {

    void invoke(Exception e);
}
