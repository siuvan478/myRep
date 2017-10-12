package com.asgab.repository;

import com.asgab.entity.City;
import com.asgab.repository.mybatis.MyBatisRepository;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface CityMapper {

    List<City> findAll(Map<String, Object> parameters);

    City get(Long id);

    Long save(City city);

    void update(City city);

    void delete(Long id);
}