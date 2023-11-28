package cn.mina.boot.job.xxl.register.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Created by haoteng on 2022/10/26.
 */
@Data
public class XxlJobGroup implements Serializable {

    private static final long serialVersionUID = 8345659090992000545L;
    private int id;
    private String appname;
    private String title;
    // 执行器地址类型：0=自动注册、1=手动录入
    private int addressType;

    // 执行器地址列表，多地址逗号分隔(手动录入)
    private String addressList;
    private Date updateTime;

    // 执行器地址列表(系统注册)
    private List<String> registryList;

    public List<String> getRegistryList() {
        if (addressList != null && addressList.trim().length() > 0) {
            registryList = new ArrayList<String>(Arrays.asList(addressList.split(",")));
        }
        return registryList;
    }
}
