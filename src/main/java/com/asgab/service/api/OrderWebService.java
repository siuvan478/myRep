package com.asgab.service.api;


import com.alibaba.fastjson.JSONObject;
import com.asgab.constants.GlobalConstants;
import com.asgab.entity.*;
import com.asgab.repository.BoxRecordMapper;
import com.asgab.repository.BoxServiceMapper;
import com.asgab.repository.OrderMapper;
import com.asgab.service.ApiException;
import com.asgab.service.ProductService;
import com.asgab.service.ScaleService;
import com.asgab.util.BeanMapper;
import com.asgab.util.DateUtils;
import com.asgab.util.LoginUtil;
import com.asgab.web.api.param.OrderBuyParam;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.MessageFormat;

@Component
@Transactional
public class OrderWebService {

    private static Logger logger = Logger.getLogger(OrderWebService.class);

    private static final BigDecimal addition_fee = new BigDecimal(60.00);

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

    public void submitOrder(OrderBuyParam param) {
        try {
            Long userId = LoginUtil.getUserId();
            if (userId == null) {
                throw new ApiException("用户未登录");
            }
            if (param.getProductId() == null || param.getScaleId() == null || param.getCycle() == null
                    || param.getTotalPrice() == null || param.getTotalPrice().doubleValue() <= 0
                    || !GlobalConstants.CycleEnum.validCycle(param.getCycle()) || param.getAppointmentTime() == null) {
                throw new ApiException("订单参数异常");
            }
            if (DateUtils.isBeforeNow(param.getAppointmentTime())) {
                throw new ApiException("指定时间不能是早于今天");
            }
            Product productInfo = productService.getProductFromCache(param.getProductId());
            if (productInfo == null) {
                logger.error(MessageFormat.format("购买的服务已下架，productId={0}不存在", param.getProductId()));
                throw new ApiException("购买的服务已下架");
            }
            Scale scaleInfo = scaleService.getScaleFromCache(param.getScaleId());
            if (scaleInfo == null) {
                logger.error(MessageFormat.format("购买的服务已下架，scaleId={0}不存在", param.getScaleId()));
                throw new ApiException("购买的服务已下架");
            }
            if (!scaleInfo.getProductId().equals(param.getProductId())) {
                logger.error(MessageFormat.format("购买的服务已下架，[orderInfo={0}/{1}，scaleInfo={3}/{4}]",
                        param.getScaleId(), param.getProductId(), scaleInfo.getId(), scaleInfo.getProductId()));
                throw new ApiException("购买的服务已下架");
            }
            if (!scaleInfo.validCycle(param.getCycle())) {
                logger.error(MessageFormat.format("不支持当前租用时间, cycle={0}", param.getCycle()));
                throw new ApiException("不支持当前租用时间");
            }
            //非周末收取额外费用
            BigDecimal additionFee = new BigDecimal(0);
            if (!DateUtils.isSunday(param.getAppointmentTime())) {
                additionFee = additionFee.add(addition_fee);
            }
            if (!scaleInfo.validTotalPrice(param.getCycle(), param.getTotalPrice(), additionFee)) {
                logger.error(MessageFormat.format("购买的服务已下架, cycle={0}, totalPrice={1}, productInfo=[\r\n{2}\r\n]",
                        param.getCycle(), param.getTotalPrice(), JSONObject.toJSONString(scaleInfo)));
                throw new ApiException("购买的服务已下架");
            }

            //文件服务
            GlobalConstants.CycleEnum cycleEnum = GlobalConstants.CycleEnum.getCycleEnum(param.getCycle());
            BoxService service = BeanMapper.map(param, BoxService.class);
            service.setUserId(userId);
            service.setStartTime(param.getAppointmentTime());
            service.setEndTime(DateUtils.plusMonths(param.getAppointmentTime(), cycleEnum.getValue()));
            service.setFlag(GlobalConstants.BoxFlag.USE);
            service.setStatus(GlobalConstants.ServiceStatus.WAIT_FOR_SAVE);//等待收货
            Long serviceId = boxServiceMapper.save(service);
            //服务记录
            BoxRecord record = new BoxRecord(userId, serviceId, GlobalConstants.RecordType.SAVE, param.getAppointmentTime(), additionFee);
            record.setStatus(GlobalConstants.RecordStatus.WAITING);
            boxRecordMapper.save(record);
            //构建订单
            Order order = BeanMapper.map(param, Order.class);
            order.setUserId(userId);
            order.setOrderNo("TODO-ORDER-NO-01");
            order.setCallbackId(serviceId);
            order.setQuantity(1);
            orderMapper.save(order);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new ApiException("提交订单失败");
        }
    }

}