package cn.mina.boot.protocol.api;

/**
 * @author Created by haoteng on 2023/2/20.
 */
public abstract class AbstractAccepter {

    protected Protocol protocol;

    /**
     * 自定义协议注册到框架
     */
    protected abstract void register();

    /**
     * 获取协议信息
     */
    public Protocol getProtocolInfo() {
        return this.protocol;
    }
}
