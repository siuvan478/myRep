package com.asgab.service;

import com.alibaba.fastjson.JSONObject;
import com.asgab.constants.CacheKey;
import com.asgab.entity.City;
import com.asgab.repository.CityMapper;
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
public class CityService implements InitializingBean {

    @Resource
    private CityMapper cityMapper;

    @Resource
    private JedisService jedisService;

    public List<City> findAll() {
        return cityMapper.search(null);
    }

    public City get(Long id) {
        return cityMapper.get(id);
    }

    public void save(City city) {
        cityMapper.save(city);
    }

    public void update(City city) {
        cityMapper.update(city);
    }

    public void delete(Long id) {
        cityMapper.delete(id);
    }

    public Map<String, String> getCitiesMapping() {
        List<City> cities = this.findAll();
        final Map<String, String> cityMappings = new TreeMap<>();
        for (City c : cities) {
            cityMappings.put(c.getId().toString(), c.getName());
        }
        return cityMappings;
    }

    public List<City> getCitiesFromCache() {
        String cacheJson = jedisService.hashGet(CacheKey.CITY_KEY, CacheKey.LIST);
        if (StringUtils.isNotBlank(cacheJson)) {
            return JSONObject.parseArray(cacheJson, City.class);
        } else {
            return null;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisService.delete(CacheKey.CITY_KEY);
        List<City> cities = this.findAll();
        if (cities != null && cities.size() > 0) {
            jedisService.hashPut(CacheKey.CITY_KEY, CacheKey.LIST, JSONObject.toJSONString(cities));
            for (City city : cities) {
                jedisService.hashPut(CacheKey.CITY_KEY, String.valueOf(city.getId()), JSONObject.toJSONString(city));
            }
        }
    }
}