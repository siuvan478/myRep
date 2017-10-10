package com.asgab.filter;


import com.alibaba.druid.util.PatternMatcher;
import com.alibaba.druid.util.ServletPathMatcher;
import com.asgab.service.JedisService;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class LoginCheckFilter implements Filter {

    protected PatternMatcher pathMatcher = new ServletPathMatcher();

    private JedisService jedisService;

    private Set<String> includePath;

    private Set<String> excludePath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
        this.jedisService = context.getBean("jedisService", JedisService.class);
        includePath = new HashSet<>(10);
        excludePath = new HashSet<>(10);
        String includePath = filterConfig.getInitParameter("includePath");
        for (String path : includePath.split(",")) {
            path = path.trim();
            if (path.length() == 0) {
                continue;
            }
            this.includePath.add(path.trim());
        }
        String excludePath = filterConfig.getInitParameter("excludePath");
        for (String path : excludePath.split(",")) {
            path = path.trim();
            if (path.length() == 0) {
                continue;
            }
            this.excludePath.add(path.trim());
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader("x-token");
        String requestURI = httpRequest.getServletPath();
        System.out.println(requestURI);
        if (filterPath(requestURI)) {
            String userJson = jedisService.get(token);
            if (userJson != null) {

            } else {

            }
            System.out.println("拦截");
        } else {
            System.out.println("不拦截");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private boolean filterPath(String requestURI) {
        if (this.includePath.contains("/*")) {
            return true;
        }
        for (String filterPath : this.includePath) {
            if (pathMatcher.matches(filterPath, requestURI)) {
                if (excludePath.contains(requestURI)) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}