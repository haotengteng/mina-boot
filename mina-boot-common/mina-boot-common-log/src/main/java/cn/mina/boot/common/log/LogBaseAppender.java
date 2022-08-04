package cn.mina.boot.common.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.encoder.Encoder;

/**
 * @author Created by haoteng on 2022/6/17.
 */
public abstract class LogBaseAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    protected Encoder<ILoggingEvent> encoder;

    @Override
    protected void append(ILoggingEvent loggingEvent) {
        byte[] byteArray = this.encoder.encode(loggingEvent);
        output(new String(byteArray));
    }

    /**
     * 实现改抽象方法，自定义输出到指定目标
     * @param log
     */
    abstract void output(String log);

    public void start() {
        super.started = true;
    }

    public void stop() {
        super.started = false;
    }


    public Encoder<ILoggingEvent> getEncoder() {
        return this.encoder;
    }

    public void setEncoder(Encoder<ILoggingEvent> encoder) {
        this.encoder = encoder;
    }
}
