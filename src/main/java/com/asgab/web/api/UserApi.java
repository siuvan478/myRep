package com.asgab.web.api;

import com.alibaba.fastjson.JSONObject;
import com.asgab.constants.CacheKey;
import com.asgab.entity.User;
import com.asgab.service.ApiException;
import com.asgab.service.JedisService;
import com.asgab.service.api.UserWebService;
import com.asgab.util.LoginUtil;
import com.asgab.util.MediaTypes;
import com.asgab.web.api.param.FindPwdParam;
import com.asgab.web.api.param.UserInfo;
import com.asgab.web.api.param.UserRegParam;
import com.asgab.web.api.param.VerifyCodeParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@RequestMapping("/api")
public class UserApi {

    @Resource
    private UserWebService userWebService;

    @Resource
    private JedisService jedisService;

    /**
     * 注册用户
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public ApiResponse<Boolean> register(@RequestBody UserRegParam param) {
        ApiResponse<Boolean> response = new ApiResponse<>(Boolean.TRUE);
        try {
            userWebService.register(param);
        } catch (ApiException e) {
            response.setData(false);
            response.setCode(e.getErrorCode());
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setData(false);
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 获取验证码
     */
    @RequestMapping(value = "/verifyCode", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public ApiResponse<Boolean> getVerifyCode(@RequestBody VerifyCodeParam param) {
        ApiResponse<Boolean> response = new ApiResponse<>(Boolean.TRUE);
        try {
            userWebService.sendVerifyCode(param);
        } catch (ApiException e) {
            response.setData(false);
            response.setCode(e.getErrorCode());
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setData(false);
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 找回密码
     */
    @RequestMapping(value = "/user/resetPwd", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public ApiResponse<Boolean> resetPwd(@RequestBody FindPwdParam userParam) {
        ApiResponse<Boolean> response = new ApiResponse<>(Boolean.TRUE);
        try {
            userWebService.resetPwd(userParam);
        } catch (ApiException e) {
            response.setData(false);
            response.setCode(e.getErrorCode());
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setData(false);
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 用户登录
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public ApiResponse<String> login(@RequestBody UserRegParam param, HttpServletResponse httpServletResponse) {
        ApiResponse<String> response = new ApiResponse<>();
        String token = UUID.randomUUID().toString().replace("-", "");
        httpServletResponse.setHeader("x-token", token);
        try {
            userWebService.login(param.getLoginName(), param.getPassword(), token);
            response.setData(token);
        } catch (ApiException e) {
            response.setCode(e.getErrorCode());
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 用户登出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public ApiResponse<Boolean> logout(HttpServletRequest request) {
        ApiResponse<Boolean> response = new ApiResponse<>(Boolean.TRUE);
        try {
            String token = request.getHeader("x-token");
            String userJson = jedisService.get(CacheKey.TOKEN_KEY + token);
            if (StringUtils.isNotBlank(userJson)) {
                jedisService.delete(CacheKey.TOKEN_KEY + token);
                User user = JSONObject.parseObject(userJson, User.class);
                if (user != null) {
                    jedisService.delete(CacheKey.USER_ID_KEY + user.getId().toString());
                }
            }
        } catch (ApiException e) {
            response.setData(false);
            response.setCode(e.getErrorCode());
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setData(false);
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 用户资料
     */
    @RequestMapping(value = "/user/profile", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<UserInfo> profile() {
        ApiResponse<UserInfo> response = new ApiResponse<>();
        try {
            response.setData(userWebService.profile(LoginUtil.getUserId()));
        } catch (ApiException e) {
            response.setCode(e.getErrorCode());
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /**
     * 更新用户资料
     */
    @RequestMapping(value = "/user/profile", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
    @ResponseBody
    public ApiResponse<Boolean> profile(@RequestBody UserInfo userInfo) {
        ApiResponse<Boolean> response = new ApiResponse<>(Boolean.TRUE);
        try {
            userInfo.setId(LoginUtil.getUserId());
            userWebService.updateUserInfo(userInfo);
        } catch (ApiException e) {
            response.setData(false);
            response.setCode(e.getErrorCode());
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setData(false);
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }
}