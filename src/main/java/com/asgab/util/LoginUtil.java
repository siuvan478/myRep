package com.asgab.util;

import com.alibaba.fastjson.JSONObject;
import com.asgab.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能：
 * 作者：Siuvan(Siuvan@lianj.com)
 * 日期：2017年10月17日 下午 5:21
 * 版权所有：广东联结网络技术有限公司 版权所有(C) 2016-2018
 */
public class LoginUtil {

    private static final ThreadLocal<String> userThreadLocal = new ThreadLocal<String>();

    private static final ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<HttpServletRequest>();

    private static final ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<HttpServletResponse>();

    /**
     * 获取用户id
     *
     * @return
     */
    public static Long getUserId() {
        UserEntity userEntity = getUser();
        if (userEntity != null) {
            return userEntity.getId();
        }
        return null;
    }

    /**
     * 获取用户名
     *
     * @return
     */
    public static String getLoginName() {
        UserEntity userEntity = getUser();
        if (userEntity != null) {
            return userEntity.getLoginName();
        }
        return null;
    }

    public static UserEntity getUser() {
        String userJson = userThreadLocal.get();
        if (userJson != null) {
            return JSONObject.parseObject(userJson, UserEntity.class);
        }
        return null;
    }

    public static void setUserJson(String userJson) {
        userThreadLocal.set(userJson);
    }

    public static void setHttpRequest(HttpServletRequest httpRequest) {
        requestThreadLocal.set(httpRequest);
    }

    public static void setHttpResponse(HttpServletResponse httpResponse) {
        responseThreadLocal.set(httpResponse);
    }
}