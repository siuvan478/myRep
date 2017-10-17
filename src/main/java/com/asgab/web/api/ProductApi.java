package com.asgab.web.api;


import com.asgab.entity.Product;
import com.asgab.entity.Scale;
import com.asgab.service.ApiException;
import com.asgab.service.ProductService;
import com.asgab.service.ScaleService;
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
    private ProductService productService;

    @Autowired
    private ScaleService scaleService;

    /**
     * 获取产品类型
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<Product>> getProducts() {
        ApiResponse<List<Product>> response = new ApiResponse<>();
        try {
            response.setData(productService.getAll());
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
    public ApiResponse<List<Scale>> getProducts(@RequestParam Long productId) {
        ApiResponse<List<Scale>> response = new ApiResponse<>();
        try {
            response.setData(productService.getProductScales(productId));
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
     * 获取产品规格详情
     */
    @RequestMapping(value = "/product/scale", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<Scale> getScaleDetail(@RequestParam Long scaleId) {
        ApiResponse<Scale> response = new ApiResponse<>();
        try {
            response.setData(scaleService.get(scaleId));
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