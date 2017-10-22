package com.asgab.web.api;

import com.asgab.service.ApiException;
import com.asgab.service.api.BoxServiceWebService;
import com.asgab.web.api.param.BoxServiceApplyParam;
import com.asgab.web.api.param.BoxServiceDetails;
import com.asgab.web.api.param.MyBoxService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 文件柜服务 API
 */
@Controller
@RequestMapping("/api")
public class BoxServiceApi {

    @Resource
    private BoxServiceWebService boxServiceWebService;

    /**
     * 我的服务
     *
     * @return
     */
    @RequestMapping(value = "/boxService/my", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<MyBoxService> myBoxServices() {
        ApiResponse<MyBoxService> response = new ApiResponse<>();
        try {
            response.setData(boxServiceWebService.getMyBoxService());
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
     * 文件柜服务申请
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/boxService/apply", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ApiResponse<Boolean> submitOrder(@RequestBody BoxServiceApplyParam param) {
        ApiResponse<Boolean> response = new ApiResponse<>(Boolean.TRUE);
        try {
            boxServiceWebService.applyService(param);
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
     * 文件柜服务详情
     *
     * @param serviceId
     * @return
     */
    @RequestMapping(value = "/boxService/details", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<BoxServiceDetails> getBoxServiceDetails(@RequestParam Long serviceId) {
        ApiResponse<BoxServiceDetails> response = new ApiResponse<>();
        try {
            response.setData(boxServiceWebService.getMyBoxServiceDetail(serviceId));
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
