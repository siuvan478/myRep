package com.asgab.service;

import com.asgab.constants.GlobalConstants;
import com.asgab.entity.BoxRecord;
import com.asgab.entity.BoxService;
import com.asgab.repository.BoxRecordMapper;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@Transactional
public class BoxRecordService {

    @Resource
    private BoxRecordMapper boxRecordMapper;

    @Resource
    private BoxServiceService boxServiceService;

    public BoxRecord get(Long id) {
        return boxRecordMapper.get(id);
    }

    public List<BoxRecord> getBoxRecordList(Long serviceId) {
        Map<String, Object> parameters = Maps.newHashMap();
        parameters.put("serviceId", serviceId);
        parameters.put("sort", "createTime desc");
        return boxRecordMapper.search(parameters);
    }

    public void boxServiceDone(BoxRecord boxRecord) {
        //服务标记状态
        BoxService boxService = boxServiceService.get(boxRecord.getServiceId());
        if (boxService != null) {
            boxService.setStatus(boxRecord.getType().equals(GlobalConstants.RecordType.SAVE) ?
                    GlobalConstants.ServiceStatus.SAVED : GlobalConstants.ServiceStatus.TAKEN);
            boxService.setFlag(boxRecord.getType().equals(GlobalConstants.RecordType.SAVE) ?
                    GlobalConstants.BoxFlag.USE : GlobalConstants.BoxFlag.FREE);
            boxService.setUpdateTime(new Date());
            boxServiceService.update(boxService);
        }
        boxRecord.setStatus(GlobalConstants.RecordStatus.COMPLETED);
        boxRecordMapper.update(boxRecord);
    }

    public void deleteByServiceId(Long serviceId) {
        boxRecordMapper.deleteByServiceId(serviceId);
    }
}