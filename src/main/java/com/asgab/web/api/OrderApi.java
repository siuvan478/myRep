package com.asgab.web.api;


import com.alibaba.fastjson.JSON;
import com.asgab.entity.Scale;
import com.asgab.service.ApiException;
import com.asgab.service.api.OrderWebService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/api")
public class OrderApi {

    @Resource
    private OrderWebService orderWebService;

    @RequestMapping(value = "/order/submit", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ApiResponse<Scale> getScaleDetail(@RequestBody Object object) {
        ApiResponse<Scale> response = new ApiResponse<>();
        try {
            System.out.println(JSON.toJSONString(object));

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
}