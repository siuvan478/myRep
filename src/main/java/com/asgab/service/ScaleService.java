package com.asgab.service;

import com.alibaba.fastjson.JSONObject;
import com.asgab.constants.CacheKey;
import com.asgab.constants.GlobalConstants;
import com.asgab.entity.Scale;
import com.asgab.repository.ScaleMapper;
import com.asgab.util.Collections3;
import com.google.common.collect.Lists;
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
public class ScaleService implements InitializingBean {

    @Resource
    private ScaleMapper scaleMapper;

    @Resource
    private JedisService jedisService;

    public void save(Scale scale) {
        Long id = scaleMapper.save(scale);
        refreshCache(id);
    }

    public Scale get(Long id) {
        return scaleMapper.get(id);
    }

    public void update(Scale scale) {
        scaleMapper.update(scale);
        refreshCache(scale.getId());
    }

    public void delete(Long id) {
        scaleMapper.delete(id);
        refreshCache(id);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        refreshCache(null);
    }

    public Scale getScaleFromCache(Long id) {
        String jsonObject = jedisService.hashGet(CacheKey.SCALE_KEY, String.valueOf(id));
        if (StringUtils.isNotBlank(jsonObject)) {
            return JSONObject.parseObject(jsonObject, Scale.class);
        } else {
            Scale scale = scaleMapper.get(id);
            if (scale != null) {
                jedisService.hashPut(CacheKey.SCALE_KEY, String.valueOf(id), JSONObject.toJSONString(scale));
                return scale;
            }
        }
        return null;
    }

    public List<Scale> getScalesFromCacheByProductId(Long productId) {
        String jsonList = jedisService.hashGet(CacheKey.SCALE_KEY, CacheKey.LIST);
        if (StringUtils.isNotBlank(jsonList)) {
            final List<Scale> finalList = Lists.newArrayList();
            List<Scale> scales = JSONObject.parseArray(jsonList, Scale.class);
            if (Collections3.isNotEmpty(scales)) {
                for (Scale scale : scales) {
                    if (scale.getStatus().equals(GlobalConstants.Status.NORMAL) &&
                            scale.getProductId().equals(productId)) {
                        finalList.add(scale);
                    }
                }
            }
            return finalList;
        } else {
            Map<String, Object> parameters = Maps.newHashMap();
            parameters.put("productId", productId);
            parameters.put("status", GlobalConstants.Status.NORMAL);
            return scaleMapper.search(parameters);
        }
    }

    /**
     * 刷新scale缓存
     *
     * @param id 为null刷新所有
     */
    private void refreshCache(Long id) {
        if (id == null) {
            jedisService.delete(CacheKey.SCALE_KEY);
            List<Scale> scales = scaleMapper.search(null);
            if (scales != null && scales.size() > 0) {
                jedisService.hashPut(CacheKey.SCALE_KEY, CacheKey.LIST, JSONObject.toJSONString(scales));
                for (Scale scale : scales) {
                    jedisService.hashPut(CacheKey.SCALE_KEY, String.valueOf(scale.getId()), JSONObject.toJSONString(scale));
                }
            }
        } else {
            Scale scale = scaleMapper.get(id);
            if (scale != null) {
                jedisService.hashPut(CacheKey.SCALE_KEY, String.valueOf(scale.getId()), JSONObject.toJSONString(scale));
            }
        }
    }

}