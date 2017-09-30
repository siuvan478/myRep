package com.asgab.repository;

import com.asgab.entity.Area;
import com.asgab.repository.mybatis.MyBatisRepository;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

@MyBatisRepository
public interface AreaMapper {

    List<Area> search(Map<String, Object> parameters);

    List<Area> search(Map<String, Object> parameters, RowBounds rowBounds);

    int count(Map<String, Object> map);

    Area get(Long id);

    Long save(Area area);

    void update(Area area);

    void delete(Long id);
}