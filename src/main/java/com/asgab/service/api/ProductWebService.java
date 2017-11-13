package com.asgab.service.api;

import com.asgab.constants.GlobalConstants;
import com.asgab.service.ApiException;
import com.asgab.service.BoxServiceService;
import com.asgab.service.ProductService;
import com.asgab.service.ScaleService;
import com.asgab.util.BeanMapper;
import com.asgab.util.LoginUtil;
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

    @Resource
    private BoxServiceService boxServiceService;

    public List<ProductInfo> getProductInfoList() {
        Long userId = LoginUtil.getUserId();
        List<ProductInfo> productInfoList = BeanMapper.mapList(productService.getProductListFromCache(), ProductInfo.class);
        if (userId != null) {
            List<Long> productIds = boxServiceService.getProductIdsByUserId(userId);
            if (productIds != null && productIds.size() > 0 && productInfoList != null && productInfoList.size() > 0) {
                for (ProductInfo pinfo : productInfoList) {
                    if (productIds.contains(pinfo.getId())) {
                        pinfo.setIsOwned(GlobalConstants.YesOrNo.YES);
                    }
                }
            }
        }
        return productInfoList;
    }

    public ProductInfo getProductInfo(Long productId) {
        ProductInfo info = BeanMapper.map(productService.getProductFromCache(productId), ProductInfo.class);
        if (info == null) {
            throw new ApiException("服務不存在");
        }
        info.setScales(scaleService.getScalesFromCacheByProductId(productId));
        return info;
    }
}