package com.asgab.web.api;


import com.asgab.service.ApiException;
import com.asgab.service.api.OrderWebService;
import com.asgab.util.LoginUtil;
import com.asgab.web.api.param.OrderBuyParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Controller
@RequestMapping("/api")
public class OrderApi {

    @Resource
    private OrderWebService orderWebService;

    @RequestMapping(value = "/order/submit", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ApiResponse<Boolean> submitOrder(@RequestBody OrderBuyParam param) {
        ApiResponse<Boolean> response = new ApiResponse<>(Boolean.TRUE);
        try {
            orderWebService.submitOrder(param);
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

    @RequestMapping(value = "/order/appointFee", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ApiResponse<BigDecimal> getAppointFee(@RequestBody OrderBuyParam param) {
        ApiResponse<BigDecimal> response = new ApiResponse<>();
        try {
            response.setData(orderWebService.getAppointFee(param.getAppointmentTime(), LoginUtil.getUserId()));
        } catch (ApiException e) {
            response.setCode(e.getErrorCode());
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setCode(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }
}