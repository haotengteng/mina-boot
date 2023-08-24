package cn.mina.boot.web.common.logging;

import cn.mina.boot.common.util.JsonUtils;
import cn.mina.boot.web.common.filter.MinaOncePerRequestFilter;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

/**
 * @author Created by haoteng on 2023/8/17.
 */
public class MinaWebLoggingFilter extends MinaOncePerRequestFilter {

    private final static Logger log = LoggerFactory.getLogger(MinaWebLoggingFilter.class);

    private String[] excludeUrls;

    public String[] getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    private PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        if (excludeUrls != null) {
            for (String pattern : excludeUrls) {
                if (Boolean.TRUE.equals(pathMatcher.match(pattern, httpServletRequest.getRequestURI()))) {
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                }
            }
        }
        long st = System.currentTimeMillis();
        LoggingHttpServletRequestWrapper requestWrapper = new LoggingHttpServletRequestWrapper(httpServletRequest);
        // 增加一层IO输出流
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);

        logRequest(requestWrapper);
        filterChain.doFilter(requestWrapper, responseWrapper);
        logResponse(httpServletResponse, st, responseWrapper);
        // 将输出流回给当前HttpServletResponse
        responseWrapper.copyBodyToResponse();
    }

    @Override
    public String urlPatterns() {
        return "/**";
    }

    @Override
    public Integer order() {
        return 1;
    }


    private void logResponse(ServletResponse response, long st, ContentCachingResponseWrapper multiResponse) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        log.info("响应信息：{}，请求耗时:{}ms", StringUtils.toEncodedString(multiResponse.getContentAsByteArray(), Charset.forName("UTF-8")), System.currentTimeMillis() - st);
    }

    private void logRequest(HttpServletRequest httpServletRequest) throws IOException {
        if (httpServletRequest.getMethod().equalsIgnoreCase("GET")) {
            MinaWebLoggingFilter.LoggerModel loggerModel = new MinaWebLoggingFilter.LoggerModel();
            loggerModel.setParams(URLDecoder.decode(!StringUtils.isNotBlank(httpServletRequest.getQueryString()) ? "" : httpServletRequest.getQueryString(), "UTF-8"));
            loggerModel.setUrl(httpServletRequest.getRequestURL().toString());
            log.info("请求信息：{}", JsonUtils.toJSONString(loggerModel));
        } else {
            StringBuilder content = new StringBuilder();
            content.append(URLDecoder.decode(!StringUtils.isNotBlank(httpServletRequest.getQueryString()) ? "" : httpServletRequest.getQueryString(), "UTF-8"));
            ServletInputStream servletInputStream = httpServletRequest.getInputStream();
            byte[] b = new byte[1024];
            int lens = -1;
            while ((lens = servletInputStream.read(b)) > 0) {
                content.append(new String(b, 0, lens));
            }
            String strcont = content.toString();
            MinaWebLoggingFilter.LoggerModel loggerModel = new MinaWebLoggingFilter.LoggerModel();
            loggerModel.setParams(strcont);
            loggerModel.setUrl(httpServletRequest.getRequestURL().toString());
            log.info("请求信息：{}", JsonUtils.toJSONString(loggerModel));
        }
    }

    @Override
    public void destroy() {

    }

    @Data
    class LoggerModel {
        private String url;
        private String params;

        public LoggerModel() {
        }
    }
}
