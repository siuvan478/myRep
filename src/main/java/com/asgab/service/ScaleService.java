package com.asgab.service;

import com.asgab.entity.Scale;
import com.asgab.repository.ScaleMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
@Transactional
public class ScaleService {

    @Resource
    private ScaleMapper scaleMapper;

    public Scale get(Long id) {
        return scaleMapper.get(id);
    }

    public void update(Scale scale) {
        scaleMapper.update(scale);
    }
}