package com.demo.filter;

import com.demo.utils.IPv4Util;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Title:Request过滤器
 * @Description:TODO
 * @author:xu.he
 * @create:2016-12-22 10:31
 * @version:v1.0
 */

public class HttpRequestFilter implements Filter {

    private Logger logger=Logger.getLogger(HttpRequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("[HttpRequestFilter.doFilter][start]");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取ApplicationContext
        ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());

        //处理跨域请求
        MDC.put("ip", IPv4Util.getIpAddr(request));
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, *");
        String requestUrl = null;
        if (request.getHeader("Origin") != null) {
            requestUrl = request.getHeader("Origin");
        } else {
            requestUrl = "*";
        }
        try {
            if ("*".equals(requestUrl) || "null".equals(requestUrl)) {
                response.setHeader("Access-Control-Allow-Origin", requestUrl);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).error("err=origin_error origin=" + requestUrl);
        }
        // P3P兼容
        response.setHeader("P3P", "CP=\"IDC DSP COR CURa ADMa OUR IND PHY ONL COM STA\"");
        logger.info("[HttpRequestFilter.doFilter][end]");
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
