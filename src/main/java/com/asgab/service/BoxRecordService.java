package com.asgab.service;

import com.asgab.entity.BoxRecord;
import com.asgab.repository.BoxRecordMapper;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class BoxRecordService {

    @Resource
    private BoxRecordMapper boxRecordMapper;

    public BoxRecord get(Long id) {
        return boxRecordMapper.get(id);
    }

    public List<BoxRecord> getBoxRecordList(Long serviceId) {
        Map<String, Object> parameters = Maps.newHashMap();
        parameters.put("serviceId", serviceId);
        parameters.put("sort", "createTime desc");
        return boxRecordMapper.search(parameters);
    }

    public void deleteByServiceId(Long serviceId) {
        boxRecordMapper.deleteByServiceId(serviceId);
    }
}