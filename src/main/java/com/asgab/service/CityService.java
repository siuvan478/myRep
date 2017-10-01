package com.asgab.service;

import com.asgab.entity.City;
import com.asgab.repository.CityMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 功能：
 * 作者：Siuvan(Siuvan@lianj.com)
 * 日期：2017年09月30日 上午 10:13
 * 版权所有：广东联结网络技术有限公司 版权所有(C) 2016-2018
 */
@Component
@Transactional
public class CityService {

    @Resource
    private CityMapper cityMapper;

    public List<City> findAll() {
        return cityMapper.findAll(null);
    }

    public City get(Long id) {
        return cityMapper.get(id);
    }

    public Long save(City city) {
        return cityMapper.save(city);
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
}