package com.asgab.service.api;


import com.alibaba.fastjson.JSONObject;
import com.asgab.constants.GlobalConstants;
import com.asgab.entity.*;
import com.asgab.repository.BoxRecordMapper;
import com.asgab.repository.BoxServiceMapper;
import com.asgab.repository.OrderMapper;
import com.asgab.service.ApiException;
import com.asgab.service.ConfigService;
import com.asgab.service.ProductService;
import com.asgab.service.ScaleService;
import com.asgab.util.BeanMapper;
import com.asgab.util.DateUtils;
import com.asgab.util.IdGenerator;
import com.asgab.util.LoginUtil;
import com.asgab.web.api.param.OrderBuyParam;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

@Component
@Transactional
public class OrderWebService {

    private static Logger logger = Logger.getLogger(OrderWebService.class);

    /**
     * 默认预约费用 $80HKD
     */
    private static final BigDecimal DEFAULT_APPOINT_FEE = new BigDecimal(80.00);

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private BoxServiceMapper boxServiceMapper;

    @Resource
    private BoxRecordMapper boxRecordMapper;

    @Resource
    private ProductService productService;

    @Resource
    private ScaleService scaleService;

    @Resource
    private ConfigService configService;

    public void submitOrder(OrderBuyParam param) {
        try {
            Long userId = LoginUtil.getUserId();
            if (userId == null) {
                throw new ApiException("請先登入");
            }
            if (param.getProductId() == null || param.getScaleId() == null || param.getCycle() == null
                    || param.getTotalPrice() == null || param.getTotalPrice().doubleValue() <= 0
                    || !GlobalConstants.CycleEnum.validCycle(param.getCycle()) || param.getAppointmentTime() == null
                    || param.getQuantity() == null || param.getQuantity() <= 0) {
                throw new ApiException("訂單參數異常");
            }
            if (DateUtils.isBeforeNow(param.getAppointmentTime())) {
                throw new ApiException("預約時間必須晚於當前時間");
            }
            Product productInfo = productService.getProductFromCache(param.getProductId());
            if (productInfo == null) {
                logger.error(MessageFormat.format("購買的服務已下架，productId={0}不存在", param.getProductId()));
                throw new ApiException("購買的服務已下架");
            }
            Scale scaleInfo = scaleService.getScaleFromCache(param.getScaleId());
            if (scaleInfo == null) {
                logger.error(MessageFormat.format("購買的服務已下架，scaleId={0}不存在", param.getScaleId()));
                throw new ApiException("購買的服務已下架");
            }
            if (!scaleInfo.getProductId().equals(param.getProductId())) {
                logger.error(MessageFormat.format("購買的服務已下架，[orderInfo={0}/{1}，scaleInfo={3}/{4}]",
                        param.getScaleId(), param.getProductId(), scaleInfo.getId(), scaleInfo.getProductId()));
                throw new ApiException("購買的服務已下架");
            }
            if (!scaleInfo.validCycle(param.getCycle())) {
                logger.error(MessageFormat.format("不支持當前購買週期, cycle={0}", param.getCycle()));
                throw new ApiException("不支持當前購買週期");
            }
            //非周末收取额外费用
            BigDecimal appointFee = this.getAppointFee(param.getAppointmentTime(), userId);
            if (!scaleInfo.validTotalPrice(param.getCycle(), param.getTotalPrice(), appointFee, param.getQuantity())) {
                logger.error(MessageFormat.format("購買的服務已下架, cycle={0}, totalPrice={1}, productInfo=[\r\n{2}\r\n]",
                        param.getCycle(), param.getTotalPrice(), JSONObject.toJSONString(scaleInfo)));
                throw new ApiException("購買的服務已下架");
            }

            //构建订单
            Order order = BeanMapper.map(param, Order.class);
            order.setUserId(userId);
            order.setOrderNo(IdGenerator.INSTANCE.nextId());
            orderMapper.save(order);

            //文件服务
            GlobalConstants.CycleEnum cycleEnum = GlobalConstants.CycleEnum.getCycleEnum(param.getCycle());
            if (cycleEnum == null) throw new ApiException("不支持當前購買週期");
            //批量構建訂單數據
            for (int i = 1; i <= param.getQuantity(); i++) {
                //文件服务
                BoxService service = BeanMapper.map(param, BoxService.class);
                service.setUserId(userId);
                service.setStartTime(param.getAppointmentTime());
                service.setEndTime(DateUtils.plusMonths(param.getAppointmentTime(), cycleEnum.getValue()));
                service.setFlag(GlobalConstants.BoxFlag.USE);
                service.setStatus(GlobalConstants.ServiceStatus.WAIT_FOR_SAVE);//等待收货
                service.setOrderId(order.getId());
                boxServiceMapper.save(service);

                //服务记录
                BoxRecord record = new BoxRecord(userId, service.getId(), GlobalConstants.RecordType.SAVE, param.getAppointmentTime(), appointFee);
                record.setStatus(GlobalConstants.RecordStatus.WAITING);
                record.setFullCost(configService.isFullCost(appointFee));
                boxRecordMapper.save(record);
            }
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new ApiException("購買失敗，請聯絡客服");
        }
    }

    /**
     * 获取用户预约的价格
     *
     * @param appointmentTime 预约时间
     * @param userId          用户ID
     * @return
     */
    public BigDecimal getAppointFee(Date appointmentTime, Long userId) {
        if (appointmentTime == null || DateUtils.isBeforeNow(appointmentTime)) {
            throw new ApiException("參數異常");
        }
        //周日免费
        if (DateUtils.isSunday(appointmentTime)) {
            return new BigDecimal(0);
        }
        Map<String, Object> countBoxServiceNumParam = Maps.newHashMap();
        countBoxServiceNumParam.put("userId", userId);
        int boxServiceNum = boxServiceMapper.count(countBoxServiceNumParam);
        Config config = configService.getConfigFromCache();
        if (config != null) {
            return boxServiceNum >= config.getNumber() ? config.getDiscountFee() : config.getCommonFee();
        }
        return DEFAULT_APPOINT_FEE;
    }

}