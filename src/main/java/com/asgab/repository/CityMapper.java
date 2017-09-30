package com.asgab.repository;

import com.asgab.entity.City;
import com.asgab.repository.mybatis.MyBatisRepository;

import java.util.List;
import java.util.Map;

/**
 * 功能：
 * 作者：Siuvan(Siuvan@lianj.com)
 * 日期：2017年09月30日 上午 9:56
 * 版权所有：广东联结网络技术有限公司 版权所有(C) 2016-2018
 */
@MyBatisRepository
public interface CityMapper {

    List<City> findAll(Map<String, Object> parameters);

    City get(Long id);

    Long save(City city);

    void update(City city);

    void delete(Long id);
}