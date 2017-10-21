package com.asgab.web.api;

import com.asgab.entity.Area;
import com.asgab.entity.City;
import com.asgab.service.ApiException;
import com.asgab.service.api.AreaWebService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/api")
public class AreaApi {

    @Resource
    private AreaWebService areaWebService;

    /**
     * 获取大区
     */
    @RequestMapping(value = "/cities", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<City>> getCities() {
        ApiResponse<List<City>> response = new ApiResponse<>();
        try {
            response.setData(areaWebService.getCities());
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
     * 获取小区
     */
    @RequestMapping(value = "/areas", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<Area>> getAreas(@RequestParam(required = false) Long cityId) {
        ApiResponse<List<Area>> response = new ApiResponse<>();
        try {
            response.setData(areaWebService.getAreasByCityId(cityId));
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