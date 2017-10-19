package com.asgab.service;

import com.alibaba.fastjson.JSONObject;
import com.asgab.constants.CacheKey;
import com.asgab.entity.Scale;
import com.asgab.repository.ScaleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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