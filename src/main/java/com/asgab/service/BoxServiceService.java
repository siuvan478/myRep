package com.asgab.service;

import com.asgab.core.pagination.Page;
import com.asgab.entity.Area;
import com.asgab.entity.BoxService;
import com.asgab.entity.Product;
import com.asgab.entity.Scale;
import com.asgab.repository.BoxServiceMapper;
import com.asgab.util.Collections3;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class BoxServiceService {

    @Resource
    private BoxServiceMapper boxServiceMapper;

    @Resource
    private ProductService productService;

    @Resource
    private ScaleService scaleService;

    @Resource
    private AreaService areaService;

    public List<BoxService> getBoxServiceList(Map<String, Object> parameters) {
        return boxServiceMapper.search(parameters);
    }

    public List<BoxService> getBoxServiceListByOrderId(Long orderId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("orderId", orderId);
        return boxServiceMapper.search(parameters);
    }

    public Page<BoxService> pageQuery(Page<BoxService> page) {
        List<BoxService> list = boxServiceMapper.search(page.getSearchMap(), page.getRowBounds());
        if (Collections3.isNotEmpty(list)) {
            for (BoxService boxService : list) {
                Product product = productService.getProductFromCache(boxService.getProductId());
                if (product != null) {
                    boxService.setProductName(product.getProductName());
                }
                Scale scale = scaleService.getScaleFromCache(boxService.getScaleId());
                if (scale != null) {
                    boxService.setScaleName(scale.getScale());
                }
            }
        }
        int count = boxServiceMapper.count(page.getSearchMap());
        page.setContent(list);
        page.setTotal(count);
        return page;
    }

    public List<BoxService> getExpireService() {
        return boxServiceMapper.getExpireService();
    }

    public void update(BoxService boxService) {
        boxServiceMapper.update(boxService);
    }

    public BoxService get(Long id) {
        BoxService bx = boxServiceMapper.get(id);
        if (bx != null) {
            Product product = productService.getProductFromCache(bx.getProductId());
            if (product != null) {
                bx.setProductName(product.getProductName());
            }
            Scale scale = scaleService.getScaleFromCache(bx.getScaleId());
            if (scale != null) {
                bx.setScaleName(scale.getScale());
            }
            Area area = areaService.getAreaFromCache(bx.getAreaId());
            if (area != null) {
                bx.setAreaName(area.getName());
            }
        }
        return boxServiceMapper.get(id);
    }

    public List<Long> getProductIdsByUserId(Long userId) {
        return boxServiceMapper.getProductIdsByUserId(userId);
    }
}