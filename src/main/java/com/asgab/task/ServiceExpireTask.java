package com.asgab.task;


import com.asgab.constants.GlobalConstants;
import com.asgab.entity.BoxService;
import com.asgab.service.BoxServiceService;
import com.asgab.util.Collections3;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class ServiceExpireTask {

    private static Logger logger = Logger.getLogger(ServiceExpireTask.class);

    @Resource
    private BoxServiceService boxServiceService;

    public void execute() {
        try {
            List<BoxService> list = boxServiceService.getExpireService();
            if (Collections3.isNotEmpty(list)) {
                for (BoxService bs : list) {
                    bs.setStatus(GlobalConstants.ServiceStatus.EXPIRY);
                    bs.setUpdateTime(new Date());
                    boxServiceService.update(bs);
                }
            }
        } catch (Exception e) {
            logger.error("文件柜服务过期定时器处理失败");
            e.printStackTrace();
        }
    }
}