package com.asgab.service;

import com.asgab.core.pagination.Page;
import com.asgab.entity.Order;
import com.asgab.entity.Product;
import com.asgab.entity.Scale;
import com.asgab.repository.OrderMapper;
import com.asgab.util.Collections3;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ProductService productService;

    @Resource
    private ScaleService scaleService;

    public List<Order> getAll() {
        return orderMapper.search(null);
    }

    public Page<Order> pageQuery(Page<Order> page) {
        List<Order> list = orderMapper.search(page.getSearchMap(), page.getRowBounds());
        if (Collections3.isNotEmpty(list)) {
            for (Order order : list) {
                Product product = productService.getProductFromCache(order.getProductId());
                if (product != null) {
                    order.setProductName(product.getProductName());
                }
                Scale scale = scaleService.getScaleFromCache(order.getScaleId());
                if (scale != null) {
                    order.setScaleName(scale.getScale());
                }
            }
        }
        int count = orderMapper.count(page.getSearchMap());
        page.setContent(list);
        page.setTotal(count);
        return page;
    }

    public Order get(Long id) {
        return orderMapper.get(id);
    }

    public void update(Order product) {
        orderMapper.update(product);
    }

}