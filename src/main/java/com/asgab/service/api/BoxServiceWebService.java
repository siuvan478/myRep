package com.asgab.service.api;

import com.asgab.constants.GlobalConstants;
import com.asgab.entity.BoxRecord;
import com.asgab.entity.BoxService;
import com.asgab.repository.BoxRecordMapper;
import com.asgab.service.ApiException;
import com.asgab.service.BoxRecordService;
import com.asgab.service.BoxServiceService;
import com.asgab.util.BeanMapper;
import com.asgab.util.DateUtils;
import com.asgab.util.LoginUtil;
import com.asgab.web.api.UserApi;
import com.asgab.web.api.param.BoxServiceApplyParam;
import com.asgab.web.api.param.BoxServiceDetails;
import com.asgab.web.api.param.MyBoxService;
import com.asgab.web.api.param.UserInfo;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Component
@Transactional
public class BoxServiceWebService {

    private static Logger logger = Logger.getLogger(BoxServiceWebService.class);

    @Resource
    private BoxServiceService boxServiceService;

    @Resource
    private BoxRecordMapper boxRecordMapper;

    @Resource
    private UserWebService userWebService;

    public MyBoxService getMyBoxService() {
        MyBoxService myBoxService = new MyBoxService();
        try {
            Long userId = LoginUtil.getUserId();
            if (userId == null) throw new ApiException("用户未登录");
            UserInfo userInfo = userWebService.profile(LoginUtil.getToken());
            BeanMapper.copy(userInfo, myBoxService);
            Map<String, Object> params = Maps.newHashMap();
            params.put("userId", userId);
            myBoxService.setBoxServices(boxServiceService.getBoxServiceList(params));
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException("网络繁忙");
        }
        return myBoxService;
    }

    public BoxServiceDetails getMyBoxServiceDetail(Long serviceId) {
        BoxServiceDetails boxServiceDetails = new BoxServiceDetails();
        try {
            Long userId = LoginUtil.getUserId();
            if (userId == null) throw new ApiException("用户未登录");
            BoxService boxService = boxServiceService.get(serviceId);
            if (boxService == null || !boxService.getUserId().equals(userId)) throw new ApiException("文件柜服务不存在");
            BeanMapper.copy(boxService, boxServiceDetails);
            Map<String, Object> params = Maps.newHashMap();
            params.put("serviceId", serviceId);
            params.put("sort", "create_time desc");
            boxServiceDetails.setLastRecord(boxRecordMapper.search(params));
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException("网络繁忙");
        }
        return boxServiceDetails;
    }

    public void applyService(BoxServiceApplyParam param) {
        BigDecimal cost = new BigDecimal(0);
        try {
            Long userId = LoginUtil.getUserId();
            if (userId == null) throw new ApiException("用户未登录");
            if (param.getServiceId() == null || param.getApplyType() == null || param.getAppointmentTime() == null) {
                throw new ApiException("预约参数异常");
            }
            if (GlobalConstants.RecordType.valid(param.getApplyType())) throw new ApiException("预约参数异常");
            if (DateUtils.isBeforeNow(param.getAppointmentTime())) throw new ApiException("预约时间不能早于今天");
            //获取文件柜服务
            final BoxService boxService = boxServiceService.get(param.getServiceId());
            if (boxService == null) throw new ApiException("文件柜服务不存在");
            if (boxService.getStatus().equals(GlobalConstants.ServiceStatus.EXPIRY)) {
                throw new ApiException("文件柜服务已过期");
            } else if (DateUtils.isBeforeNow(boxService.getEndTime())) {
                throw new ApiException("文件柜服务已过期");
            }
            //保存预约记录
            BoxRecord record = new BoxRecord(userId, param.getServiceId(), param.getApplyType(), param.getAppointmentTime(), cost);
            record.setStatus(GlobalConstants.RecordStatus.WAITING);
            boxRecordMapper.save(record);
            //更新文件柜服务状态
            if (GlobalConstants.RecordType.SAVE.equals(param.getApplyType())) {
                //等待收件
                boxService.setStatus(GlobalConstants.ServiceStatus.WAIT_FOR_SAVE);
            } else if (GlobalConstants.RecordType.TAKE.equals(param.getApplyType())) {
                //等待提货
                boxService.setStatus(GlobalConstants.ServiceStatus.WAIT_FOR_TAKE);
            }
            boxService.setUpdateTime(new Date());
            boxServiceService.update(boxService);
        } catch (ApiException e) {
            logger.error("预约文件柜服务出错", e);
            throw e;
        } catch (Exception e) {
            logger.error("预约失败", e);
            throw new ApiException("预约失败，请联系客服");
        }
    }
}
