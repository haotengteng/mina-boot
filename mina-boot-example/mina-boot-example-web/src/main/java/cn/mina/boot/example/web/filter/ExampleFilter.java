package cn.mina.boot.example.web.filter;

import cn.mina.boot.web.common.log.LoggingHttpServletRequestWrapper;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@Order(2)
@WebFilter(filterName = "ExampleFilter", urlPatterns = "/*")
public class ExampleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("filter1:===============");
//        forward(request, response, filterChain);
        filterChain.doFilter(request, response);
        System.out.println("filter2:===============");
    }

    private static void forward(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if ("/foo/hello".equals(((LoggingHttpServletRequestWrapper) request).getRequestURI())) {
            // 按指定路径转发
            request.getRequestDispatcher("/foo/hello/client").forward(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
