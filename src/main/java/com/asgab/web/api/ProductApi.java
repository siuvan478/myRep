package com.asgab.web.api;


import com.asgab.service.ApiException;
import com.asgab.service.ScaleService;
import com.asgab.service.api.ProductWebService;
import com.asgab.web.api.param.ProductInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api")
public class ProductApi {

    @Autowired
    private ProductWebService productWebService;

    /**
     * 获取产品类型
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<ProductInfo>> getProductInfoList() {
        ApiResponse<List<ProductInfo>> response = new ApiResponse<>();
        try {
            response.setData(productWebService.getProductInfoList());
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
     * 获取产品规格
     */
    @RequestMapping(value = "/product/scales", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<ProductInfo> productDetail(@RequestParam Long productId) {
        ApiResponse<ProductInfo> response = new ApiResponse<>();
        try {
            response.setData(productWebService.getProductInfo(productId));
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