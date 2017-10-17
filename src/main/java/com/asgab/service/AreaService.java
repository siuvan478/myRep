package com.asgab.service;

import com.alibaba.fastjson.JSONObject;
import com.asgab.constants.CacheKey;
import com.asgab.core.pagination.Page;
import com.asgab.entity.Area;
import com.asgab.repository.AreaMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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