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
import java.util.TreeMap;


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
        refreshCache(product.getId());
    }

    public List<Product> getAll(Map<String, Object> searchMap) {
        return productMapper.search(searchMap);
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
        refreshCache(product.getId());
    }

    public void delete(Long id) {
        productMapper.delete(id);
        refreshCache(id);
    }

    public List<Scale> getProductScales(Long productId) {
        Map<String, Object> parameters = Maps.newHashMap();
        parameters.put("productId", productId);
        return scaleMapper.search(parameters);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        refreshCache(null);
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

    public List<Product> getProductListFromCache() {
        long s1 = System.currentTimeMillis();
        String jsonList = jedisService.hashGet(CacheKey.PRODUCT_KEY, CacheKey.LIST);
        long s2 = System.currentTimeMillis();
        System.out.println("-------------------------------------" + (s2 - s1));
        if (StringUtils.isNoneBlank(jsonList)) {
            long s3 = System.currentTimeMillis();
            List<Product> products = JSONObject.parseArray(jsonList, Product.class);
            System.out.println("-------------------------------------" + (s3 - s2));
            return products;
        } else {
            return productMapper.search(null);
        }
    }

    public Map<String, String> getProducts() {
        List<Product> products = this.getProductListFromCache();
        final Map<String, String> cityMappings = new TreeMap<>();
        for (Product p : products) {
            cityMappings.put(p.getId().toString(), p.getProductName());
        }
        return cityMappings;
    }

    /**
     * 刷新product缓存
     *
     * @param id 为null刷新所有
     */
    private void refreshCache(Long id) {
        if (id == null) {
            jedisService.delete(CacheKey.PRODUCT_KEY);
            List<Product> products = productMapper.search(null);
            if (products != null && products.size() > 0) {
                jedisService.hashPut(CacheKey.PRODUCT_KEY, CacheKey.LIST, JSONObject.toJSONString(products));
                for (Product product : products) {
                    jedisService.hashPut(CacheKey.PRODUCT_KEY, String.valueOf(product.getId()), JSONObject.toJSONString(product));
                }
            }
        } else {
            Product product = productMapper.get(id);
            if (product != null) {
                jedisService.hashPut(CacheKey.PRODUCT_KEY, String.valueOf(id), JSONObject.toJSONString(product));
            }
        }
    }

}