package com.asgab.service.api;

import com.asgab.service.ApiException;
import com.asgab.service.ProductService;
import com.asgab.service.ScaleService;
import com.asgab.util.BeanMapper;
import com.asgab.web.api.param.ProductInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ProductWebService {

    @Resource
    private ProductService productService;

    @Resource
    private ScaleService scaleService;

    public List<ProductInfo> getProductInfoList() {
        return BeanMapper.mapList(productService.getProductListFromCache(), ProductInfo.class);
    }

    public ProductInfo getProductInfo(Long productId) {
        ProductInfo info = BeanMapper.map(productService.getProductFromCache(productId), ProductInfo.class);
        if (info == null) {
            throw new ApiException("产品不存在");
        }
        info.setScales(scaleService.getScalesFromCacheByProductId(productId));
        return info;
    }
}