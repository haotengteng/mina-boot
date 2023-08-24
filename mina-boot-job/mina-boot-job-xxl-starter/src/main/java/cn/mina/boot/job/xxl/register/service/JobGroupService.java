package cn.mina.boot.job.xxl.register.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.mina.boot.common.util.JsonUtils;
import cn.mina.boot.job.xxl.MinaJobXxlProperties;
import cn.mina.boot.job.xxl.register.model.XxlJobGroup;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author Created by haoteng on 2022/10/26.
 */
public class JobGroupService {
    @Autowired
    private JobLoginService jobLoginService;
    @Autowired
    private MinaJobXxlProperties properties;


    public List<XxlJobGroup> getJobGroup() {
        String url = properties.getAdminAddresses() + "/jobgroup/pageList";
        HttpResponse response = HttpRequest.post(url)
                .form("appname", properties.getAppname())
                .form("title", properties.getTitle())
                .cookie(jobLoginService.getCookie())
                .execute();

        String body = response.body();
        Iterator<JsonNode> elements = JsonUtils.toJsonNode(body).get("data").elements();
        List<XxlJobGroup> list = new ArrayList<>();
        elements.forEachRemaining(element -> {
            XxlJobGroup xxlJobGroup = JsonUtils.toBean(element, XxlJobGroup.class);
            list.add(xxlJobGroup);
        });
        return list;
    }


    public boolean autoRegisterGroup() {
        String url = properties.getAdminAddresses() + "/jobgroup/save";
        HttpResponse response = HttpRequest.post(url)
                .form("appname", properties.getAppname())
                .form("title", properties.getTitle())
                .cookie(jobLoginService.getCookie())
                .execute();
        String code = JsonUtils.toJsonNode(response.body()).get("code").asText();
        return "200".equals(code);
    }


    public boolean preciselyCheck() {
        List<XxlJobGroup> jobGroup = getJobGroup();
        Optional<XxlJobGroup> has = jobGroup.stream()
                .filter(xxlJobGroup -> xxlJobGroup.getAppname().equals(properties.getAppname())
                        && xxlJobGroup.getTitle().equals(properties.getTitle()))
                .findAny();
        return has.isPresent();
    }
}
