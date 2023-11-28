package cn.mina.boot.common.util;

import org.apache.commons.io.IOUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * @author Created by haoteng on 2023/2/19.
 */
public class HostUtils {
    public static final String IP;
    public static final String HOSTNAME;
//    private static final Logger log = LoggerFactory.getLogger(HostUtils.class);

    // todo 使用工厂方法 懒加载方式获取 IP
    static {
        String ip;
        String hostname;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();
            hostname = addr.getHostName();
        } catch (UnknownHostException e) {
            ip = "UNKNOWN";
            hostname = "UNKNOWN";
        }
        if (ip.equals("127.0.0.1") || ip.equals("::1") || ip.equals("UNKNOWN")) {
            try {
                Process process = Runtime.getRuntime().exec("hostname -i");
                if (process.waitFor() == 0) {
                    ip = new String(IOUtils.toByteArray(process.getInputStream()), StandardCharsets.UTF_8);
                }
                process = Runtime.getRuntime().exec("hostname");
                if (process.waitFor() == 0) {
                    hostname = (new String(IOUtils.toByteArray(process.getInputStream()), StandardCharsets.UTF_8)).trim();
                }
            } catch (Exception e) {
//                log.warn("get hostname failed {}", e.getMessage());
            }
        }
        IP = ip;
        HOSTNAME = hostname;
    }
}
