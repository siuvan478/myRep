package com.asgab.web.api;

import com.asgab.service.ApiException;
import com.asgab.service.api.UserWebService;
import com.asgab.web.api.param.UserParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 功能：
 * 作者：Siuvan(Siuvan@lianj.com)
 * 日期：2017年09月30日 下午 3:40
 * 版权所有：广东联结网络技术有限公司 版权所有(C) 2016-2018
 */
@Controller
@RequestMapping("/api/user/pwd")
public class UserApi {

    @Resource
    private UserWebService userWebService;

    /**
     * 发送验证码
     */
    @RequestMapping(value = "/sendVerifyCode", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ApiResponse<Boolean> sendVerifyCode(@RequestBody UserParam userParam) {
        ApiResponse<Boolean> response = new ApiResponse<>(Boolean.TRUE);
        try {
            userWebService.sendVerifyCode(userParam);
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
    @RequestMapping(value = "/find", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ApiResponse<Boolean> findPassword(@RequestBody UserParam userParam) {
        ApiResponse<Boolean> response = new ApiResponse<>(Boolean.TRUE);
        try {
            //检查验证码
            final boolean validFlag = userWebService.checkVerifyCode(userParam);
            if (!validFlag) {
                throw new ApiException("验证码错误");
            }
            userWebService.findPassword(userParam);
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