package cn.mina.boot.protocol.http.accepter;

import cn.mina.boot.protocol.api.AbstractAccepter;
import cn.mina.boot.protocol.api.Direction;
import cn.mina.boot.protocol.api.Protocol;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Created by haoteng on 2023/2/20.
 */
@Slf4j
public class HttpProtocolAccepter extends AbstractAccepter {
    public HttpProtocolAccepter() {
        this.register();
    }

    @Override
    protected void register() {
        this.protocol = Protocol.builder()
                .name("http")
                .direction(Direction.ACCEPT)
                .desc("http protocol accepter")
                .build();
    }

}
