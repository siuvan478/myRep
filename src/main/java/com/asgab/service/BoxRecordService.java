package com.asgab.service;

import com.asgab.entity.BoxRecord;
import com.asgab.repository.BoxRecordMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Component
@Transactional
public class BoxRecordService {

    @Resource
    private BoxRecordMapper boxRecordMapper;

    public BoxRecord get(Long id) {
        return boxRecordMapper.get(id);
    }

    public void deleteByServiceId(Long serviceId) {
        boxRecordMapper.deleteByServiceId(serviceId);
    }
}