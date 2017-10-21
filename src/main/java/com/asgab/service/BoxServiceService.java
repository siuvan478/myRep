package com.asgab.service;

import com.asgab.core.pagination.Page;
import com.asgab.entity.BoxService;
import com.asgab.repository.BoxServiceMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class BoxServiceService {

    @Resource
    private BoxServiceMapper boxServiceMapper;

    public Page<BoxService> pageQuery(Page<BoxService> page) {
        List<BoxService> list = boxServiceMapper.search(page.getSearchMap(), page.getRowBounds());
        int count = boxServiceMapper.count(page.getSearchMap());
        page.setContent(list);
        page.setTotal(count);
        return page;
    }

    public List<BoxService> getExpireService() {
        return boxServiceMapper.getExpireService();
    }

    public void update(BoxService boxService) {
        boxServiceMapper.update(boxService);
    }

    public BoxService get(Long id) {
        return boxServiceMapper.get(id);
    }
}