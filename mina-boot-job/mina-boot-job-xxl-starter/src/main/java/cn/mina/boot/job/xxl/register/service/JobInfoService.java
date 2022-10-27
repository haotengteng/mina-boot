package cn.mina.boot.job.xxl.register.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.mina.boot.common.util.JsonUtil;
import cn.mina.boot.job.xxl.MinaJobXxlProperties;
import cn.mina.boot.job.xxl.register.model.XxlJobInfo;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Created by haoteng on 2022/10/26.
 */
public class JobInfoService {

    @Autowired
    private JobLoginService jobLoginService;
    @Autowired
    private MinaJobXxlProperties properties;

    public List<XxlJobInfo> getJobInfo(Integer jobGroupId, String executorHandler) {
        String url = properties.getAdminAddresses() + "/jobinfo/pageList";
        HttpResponse response = HttpRequest.post(url)
                .form("jobGroup", jobGroupId)
                .form("executorHandler", executorHandler)
                .form("triggerStatus", -1)
                .cookie(jobLoginService.getCookie())
                .execute();

        String body = response.body();
        Iterator<JsonNode> elements = JsonUtil.toJsonNode(body).get("data").elements();
        List<XxlJobInfo> list = new ArrayList<>();
        elements.forEachRemaining(element -> {
            XxlJobInfo xxlJobInfo = JsonUtil.toBean(element, XxlJobInfo.class);
            list.add(xxlJobInfo);
        });
        return list;
    }

    public Integer addJobInfo(XxlJobInfo xxlJobInfo) {
        String url = properties.getAdminAddresses() + "/jobinfo/add";
        Map<String, Object> paramMap = BeanUtil.beanToMap(xxlJobInfo);
        HttpResponse response = HttpRequest.post(url)
                .form(paramMap)
                .cookie(jobLoginService.getCookie())
                .execute();

        JsonNode jsonNode = JsonUtil.toJsonNode(response.body());
        String code = jsonNode.get("code").asText();
        if ("200".equals(code)) {
            return jsonNode.get("content").asInt();
        }
        throw new RuntimeException("mina-job xxl add jobInfo error!");
    }

}
