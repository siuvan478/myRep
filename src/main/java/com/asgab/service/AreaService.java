package com.asgab.service;

import com.asgab.core.pagination.Page;
import com.asgab.entity.Area;
import com.asgab.repository.AreaMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class AreaService {

    @Resource
    private AreaMapper areaMapper;

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
}