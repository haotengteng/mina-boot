package cn.mina.boot.protocol.framework;

import cn.mina.boot.protocol.api.AbstractAccepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Created by haoteng on 2023/2/20.
 */
@Configuration
public class ProtocolLoader {
    @Autowired
    private List<AbstractAccepter> accepters;

    @PostConstruct
    public void loaderProtocol() {
        accepters.stream().forEach(accepter -> {
            System.out.println(accepter.getProtocolInfo());
        });
//        ServiceLoader<IProtocolAccepter> loader = ServiceLoader.load(IProtocolAccepter.class);
//        for (IProtocolAccepter accepter : loader) {
//            accepter.load();
//            Protocol protocol = new Protocol();
//            protocol.setName(accepter.protocol());
//            protocol.setDirection(Direction.ACCEPT);
//            protocol.setDesc(accepter.desc());
//        }
    }
}
