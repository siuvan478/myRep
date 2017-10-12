package com.asgab.service;

import com.asgab.core.pagination.Page;
import com.asgab.entity.Product;
import com.asgab.entity.Scale;
import com.asgab.repository.ProductMapper;
import com.asgab.repository.ScaleMapper;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Component
@Transactional
public class ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ScaleMapper scaleMapper;

    public void save(Product product) {
        productMapper.save(product);
    }

    public List<Product> getAll() {
        return productMapper.search(null);
    }

    public Page<Product> getAll(Page<Product> page) {
        List<Product> list = productMapper.search(page.getSearchMap(), page.getRowBounds());
        int count = productMapper.count(page.getSearchMap());
        page.setContent(list);
        page.setTotal(count);
        return page;
    }

    public Product get(Long id) {
        return productMapper.get(id);
    }

    public void update(Product product) {
        productMapper.update(product);
    }

    public void delete(Long id) {
        productMapper.delete(id);
    }

    public List<Scale> getProductScales(Long productId) {
        Map<String, Object> parameters = Maps.newHashMap();
        parameters.put("productId", productId);
        return scaleMapper.search(parameters);
    }

    public Scale getScale(Long scaleId) {
        return scaleMapper.get(scaleId);
    }
}