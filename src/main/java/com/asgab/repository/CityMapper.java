package com.asgab.repository;

import com.asgab.entity.City;
import com.asgab.repository.mybatis.MyBatisRepository;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface CityMapper {

    List<City> search(Map<String, Object> parameters);

    List<City> search(Map<String, Object> parameters, RowBounds rowBounds);

    int count(Map<String, Object> map);

    City get(Long id);

    Long save(City city);

    void update(City city);

    void delete(Long id);
}