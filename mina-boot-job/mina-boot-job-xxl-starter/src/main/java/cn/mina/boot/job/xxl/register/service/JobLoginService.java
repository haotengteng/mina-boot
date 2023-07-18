package cn.mina.boot.job.xxl.register.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.mina.boot.common.exception.MinaBaseException;
import cn.mina.boot.job.xxl.MinaJobXxlProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Created by haoteng on 2022/10/24.
 */
public class JobLoginService {

    @Autowired
    private MinaJobXxlProperties properties;

    private final String TOKEN_KEY = "XXL_JOB_LOGIN_IDENTITY";
    private final Map<String, String> loginCookie = new HashMap<>();

    public void login() {
        String url = properties.getAdminAddresses().concat("/login");
        HttpResponse response = HttpRequest.post(url)
                .form("userName", properties.getUsername())
                .form("password", properties.getPassword())
                .execute();
        List<HttpCookie> cookies = response.getCookies();
        Optional<HttpCookie> cookieOpt = cookies.stream()
                .filter(cookie -> cookie.getName().equals(TOKEN_KEY)).findFirst();
        if (!cookieOpt.isPresent()) {
            throw new MinaBaseException("mina-job get xxl-job cookie error!");
        }
        String value = cookieOpt.get().getValue();
        loginCookie.put(TOKEN_KEY, value);
    }

    public String getCookie() {
        for (int i = 0; i < 3; i++) {
            String cookieStr = loginCookie.get(TOKEN_KEY);
            if (cookieStr != null) {
                return TOKEN_KEY.concat("=").concat(cookieStr);
            }
            login();
        }
        throw new MinaBaseException("mina-job get xxl-job cookie error!");
    }
}
