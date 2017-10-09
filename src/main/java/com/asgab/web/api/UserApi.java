package com.asgab.web.api;

import com.asgab.service.ApiException;
import com.asgab.service.api.UserWebService;
import com.asgab.web.api.param.FindPwdParam;
import com.asgab.web.api.param.UserRegParam;
import com.asgab.web.api.param.VerifyCodeParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api")
public class UserApi {

    @Resource
    private UserWebService userWebService;

    /**
     * 注册用户
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST, produces = "application/json")
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
    @RequestMapping(value = "/verifyCode", method = RequestMethod.POST, produces = "application/json")
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
    @RequestMapping(value = "/user/resetPwd", method = RequestMethod.POST, produces = "application/json")
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
}