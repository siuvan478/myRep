package com.asgab.web;

import com.asgab.util.Servlets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.Model;

import javax.servlet.ServletRequest;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {

    protected static final String PAGE_NUMBER = "1";

    protected static final String PAGE_SIZE = "10";

    protected static final String SORT_COLUMN = "createTime";

    protected Map<String, Object> dealSearchCondition(ServletRequest request, Model model, String... parameters) {
        Map<String, Object> params = new HashMap<>();
        if (parameters != null && parameters.length > 0) {
            for (String parameter : parameters) {
                if (StringUtils.isNotBlank(request.getParameter(parameter))) {
                    params.put(parameter, request.getParameter(parameter));
                }
            }
        }
        // 将搜索条件编码成字符串，用于排序，分页的URL
        model.addAttribute("search", Servlets.encodeParameterString(params));
        return params;
    }
}