package com.asgab.service;

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