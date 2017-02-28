package com.demo.inteceptor;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Title:请求拦截器
 * @Description:TODO
 * @author:xu.he
 * @create:2016-12-22 09:18
 * @version:v1.0
 */
public class HttpRequestInteceptor implements HandlerInterceptor {

    private Logger logger=Logger.getLogger(HttpRequestInteceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        logger.info("[HttpRequestInteceptor.preHandle][start]");
        //打印请求参数
        printRequestParam(request);
        logger.info("[HttpRequestInteceptor.preHandle][end]");
        return true;
    }

    /**
     * 打印请求参数
     * @param request
     */
    private void printRequestParam(HttpServletRequest request){
        Map<String, String[]> params = request.getParameterMap();
        String queryString = "";
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                if (key.indexOf("password") != -1) {
                    queryString += key + "=" + "******" + "&";
                } else {
                    queryString += key + "=" + value + "&";
                }
            }
        }
        // 去掉最后一个空格
        if (queryString.length() > 1)
            queryString = queryString.substring(0, queryString.length() - 1);

        Cookie[] cookies = request.getCookies();
        String requestCookie = "";
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                requestCookie += "&" + cookie.getName() + "=" + cookie.getValue();
            }
        }
        if (requestCookie.length() > 1)
            requestCookie = requestCookie.substring(1);
        logger.info("request=" + request.getRequestURL().toString() + " param=" + queryString + " cookie=" + requestCookie);
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("[HttpRequestInteceptor.postHandle]");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.info("[HttpRequestInteceptor.afterCompletion]");
    }
}
