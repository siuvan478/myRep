package com.asgab.filter;


import com.alibaba.druid.util.PatternMatcher;
import com.alibaba.druid.util.ServletPathMatcher;
import com.alibaba.fastjson.JSON;
import com.asgab.constants.CacheKey;
import com.asgab.service.JedisService;
import com.asgab.util.LoginUtil;
import com.asgab.util.WafRequestWrapper;
import com.asgab.web.api.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
        WafRequestWrapper httpRequestWrapper = new WafRequestWrapper( httpRequest );
        LoginUtil.setHttpRequest(httpRequestWrapper);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        LoginUtil.setHttpResponse(httpResponse);
        String token = httpRequest.getHeader("x-token");
        String requestURI = httpRequest.getServletPath();
        if (filterPath(requestURI)) {
            if (StringUtils.isBlank(token)) {
                this.redirect(httpRequest, httpResponse, "請先登入");
                return;
            }
            String userJson = jedisService.get(CacheKey.TOKEN_KEY + token);
            if (StringUtils.isBlank(userJson)) {
                this.redirect(httpRequest, httpResponse, "請先登入");
                return;
            }
            LoginUtil.setUserJson(userJson);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private void redirect(HttpServletRequest httpRequest, HttpServletResponse httpResponse, String appMessage) throws IOException {
        ApiResponse<String> responseData = new ApiResponse<>(401, appMessage, null);
        httpResponse.setContentType("text/html;charset=UTF-8");
        PrintWriter out = httpResponse.getWriter();
        out.print(JSON.toJSONString(responseData));
        out.flush();
        out.close();
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