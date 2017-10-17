package com.asgab.service;

import com.alibaba.fastjson.JSONObject;
import com.asgab.constants.CacheKey;
import com.asgab.core.pagination.Page;
import com.asgab.entity.Product;
import com.asgab.entity.Scale;
import com.asgab.repository.ProductMapper;
import com.asgab.repository.ScaleMapper;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Component
@Transactional
public class ProductService implements InitializingBean {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private ScaleMapper scaleMapper;

    @Resource
    private JedisService jedisService;

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

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisService.delete(CacheKey.PRODUCT_KEY);
        List<Product> products = this.getAll();
        if (products != null && products.size() > 0) {
            jedisService.hashPut(CacheKey.PRODUCT_KEY, CacheKey.LIST, JSONObject.toJSONString(products));
            for (Product product : products) {
                jedisService.hashPut(CacheKey.PRODUCT_KEY, String.valueOf(product.getId()), JSONObject.toJSONString(product));
            }
        }
    }

    public Product getProductFromCache(Long id) {
        String jsonObject = jedisService.hashGet(CacheKey.PRODUCT_KEY, String.valueOf(id));
        if (StringUtils.isNotBlank(jsonObject)) {
            return JSONObject.parseObject(jsonObject, Product.class);
        } else {
            Product product = productMapper.get(id);
            if (product != null) {
                jedisService.hashPut(CacheKey.PRODUCT_KEY, String.valueOf(id), JSONObject.toJSONString(product));
                return product;
            }
        }
        return null;
    }

}