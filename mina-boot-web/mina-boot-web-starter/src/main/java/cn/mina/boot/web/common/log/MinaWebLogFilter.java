package cn.mina.boot.web.common.log;


import cn.mina.boot.common.util.JsonUtil;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * 入参/响应日志打印
 *
 * @author created by htt on 2018/8/2
 */
public class MinaWebLogFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(MinaWebLogFilter.class);

    private String[] excludeUrls;

    private PathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String exUrls = filterConfig.getInitParameter("excludeUrls");
        if (StringUtils.hasLength(exUrls)) {
            this.excludeUrls = exUrls.split(",");
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long st = System.currentTimeMillis();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        LoggingHttpServletRequestWrapper multiReadRequest = new LoggingHttpServletRequestWrapper(httpServletRequest);
        LoggingHttpServletResponseWrapper multiResponse = new LoggingHttpServletResponseWrapper(httpServletResponse);
        if (excludeUrls != null) {
            for (String pattern : excludeUrls) {
                if (Boolean.TRUE.equals(pathMatcher.match(pattern, httpServletRequest.getRequestURI()))) {
                    chain.doFilter(httpServletRequest, httpServletResponse);
                    return;
                }
            }
        }
        logRequest(httpServletRequest, multiReadRequest);
        chain.doFilter(multiReadRequest, multiResponse);
        logResponse(response, st, multiResponse);
    }

    private void logResponse(ServletResponse response, long st, LoggingHttpServletResponseWrapper multiResponse) throws IOException {
        response.getOutputStream().write(multiResponse.getContentAsBytes());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        log.info("响应信息：{}，请求耗时:{}ms", multiResponse.getContent(), System.currentTimeMillis() - st);
    }

    private void logRequest(HttpServletRequest httpServletRequest, LoggingHttpServletRequestWrapper multiReadRequest) throws IOException {
        if (httpServletRequest.getMethod().equalsIgnoreCase("GET")) {
            LoggerModel loggerModel = new LoggerModel();
            loggerModel.setParams(URLDecoder.decode(!StringUtils.hasLength(httpServletRequest.getQueryString()) ? "" : httpServletRequest.getQueryString(), "UTF-8"));
            loggerModel.setUrl(httpServletRequest.getRequestURL().toString());
            log.info("请求信息：{}", JsonUtil.toJSONString(loggerModel));
        } else {
            StringBuilder content = new StringBuilder();
            ServletInputStream servletInputStream = multiReadRequest.getInputStream();
            byte[] b = new byte[1024];
            int lens = -1;
            while ((lens = servletInputStream.read(b)) > 0) {
                content.append(new String(b, 0, lens));
            }
            String strcont = content.toString();
            LoggerModel loggerModel = new LoggerModel();
            loggerModel.setParams(strcont);
            loggerModel.setUrl(httpServletRequest.getRequestURL().toString());
            log.info("请求信息：{}", JsonUtil.toJSONString(loggerModel));
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
