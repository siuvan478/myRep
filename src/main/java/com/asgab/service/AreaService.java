package com.asgab.service;

import com.alibaba.fastjson.JSONObject;
import com.asgab.constants.CacheKey;
import com.asgab.constants.GlobalConstants;
import com.asgab.core.pagination.Page;
import com.asgab.entity.Area;
import com.asgab.repository.AreaMapper;
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
public class AreaService implements InitializingBean {

    @Resource
    private AreaMapper areaMapper;

    @Resource
    private JedisService jedisService;

    public List<Area> findAll() {
        return areaMapper.search(null);
    }

    public Page<Area> pageQuery(Page<Area> page) {
        List<Area> list = areaMapper.search(page.getSearchMap(), page.getRowBounds());
        int count = areaMapper.count(page.getSearchMap());
        page.setContent(list);
        page.setTotal(count);
        return page;
    }

    public Area get(Long id) {
        return areaMapper.get(id);
    }

    public Long save(Area area) {
        return areaMapper.save(area);
    }

    public void update(Area area) {
        areaMapper.update(area);
    }

    public void delete(Long id) {
        areaMapper.delete(id);
    }

    public Map<String, String> getListMapping() {
        List<Area> areas = this.getAreaListFromCache();
        final Map<String, String> result = new TreeMap<>();
        for (Area a : areas) {
            result.put(a.getId().toString(), a.getName());
        }
        return result;
    }

    public List<Area> getAreaListFromCache() {
        String jsonList = jedisService.hashGet(CacheKey.AREA_KEY, CacheKey.LIST);
        if (StringUtils.isNotBlank(jsonList)) {
            return JSONObject.parseArray(jsonList, Area.class);
        } else {
            Map<String, Object> params = Maps.newHashMap();
            params.put("status", GlobalConstants.Status.NORMAL);
            return areaMapper.search(params);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisService.delete(CacheKey.AREA_KEY);
        List<Area> areas = this.findAll();
        if (areas != null && areas.size() > 0) {
            jedisService.hashPut(CacheKey.AREA_KEY, CacheKey.LIST, JSONObject.toJSONString(areas));
            for (Area area : areas) {
                jedisService.hashPut(CacheKey.AREA_KEY, String.valueOf(area.getId()), JSONObject.toJSONString(area));
            }
        }
    }
}