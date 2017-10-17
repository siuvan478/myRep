package com.asgab.service.api;


import com.alibaba.fastjson.JSONObject;
import com.asgab.constants.CacheKey;
import com.asgab.entity.Area;
import com.asgab.entity.City;
import com.asgab.service.AreaService;
import com.asgab.service.CityService;
import com.asgab.service.JedisService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class AreaWebService {

    @Resource
    private CityService cityService;

    @Resource
    private AreaService areaService;

    @Resource
    private JedisService jedisService;

    public List<City> getCities() {
        String jsonArray = jedisService.hashGet(CacheKey.CITY_KEY, CacheKey.LIST);
        if (StringUtils.isNotBlank(jsonArray)) {
            return JSONObject.parseArray(jsonArray, City.class);
        } else {
            return cityService.findAll();
        }
    }

    public List<Area> getAreasByCityId(Long cityId) {
        String jsonArray = jedisService.hashGet(CacheKey.AREA_KEY, CacheKey.LIST);
        final List<Area> finalList = Lists.newArrayList();
        if (StringUtils.isNotBlank(jsonArray)) {
            final List<Area> areas = JSONObject.parseArray(jsonArray, Area.class);
            if (areas != null && areas.size() > 0) {
                for (Area a : areas) {
                    if (a.getCityId().equals(cityId)) {
                        finalList.add(a);
                    }
                }
            }
        }
        return finalList;
    }

}