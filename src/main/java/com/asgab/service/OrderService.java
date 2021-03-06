package com.asgab.service;

import com.asgab.constants.GlobalConstants;
import com.asgab.core.JPush;
import com.asgab.core.pagination.Page;
import com.asgab.entity.*;
import com.asgab.repository.OrderMapper;
import com.asgab.service.account.AccountService;
import com.asgab.util.Collections3;
import com.asgab.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class OrderService {

    private static final Logger logger = Logger.getLogger(OrderService.class);

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ProductService productService;

    @Resource
    private ScaleService scaleService;

    @Resource
    private BoxServiceService boxServiceService;

    @Resource
    private BoxRecordService boxRecordService;

    @Resource
    private AccountService accountService;

    public List<Order> getAll() {
        return orderMapper.search(null);
    }

    public Page<Order> pageQuery(Page<Order> page) {
        List<Order> list = orderMapper.search(page.getSearchMap(), page.getRowBounds());
        if (Collections3.isNotEmpty(list)) {
            for (Order order : list) {
                Product product = productService.getProductFromCache(order.getProductId());
                if (product != null) {
                    order.setProductName(product.getProductName());
                }
                Scale scale = scaleService.getScaleFromCache(order.getScaleId());
                if (scale != null) {
                    order.setScaleName(scale.getScale());
                }
            }
        }
        int count = orderMapper.count(page.getSearchMap());
        page.setContent(list);
        page.setTotal(count);
        return page;
    }

    public Order get(Long id) {
        Order order = orderMapper.get(id);
        if (order != null) {
            Product product = productService.getProductFromCache(order.getProductId());
            if (product != null) {
                order.setProductName(product.getProductName());
            }
            Scale scale = scaleService.getScaleFromCache(order.getScaleId());
            if (scale != null) {
                order.setScaleName(scale.getScale());
            }
        }
        return order;
    }

    public void update(Order product) {
        orderMapper.update(product);
    }

    public void audit(Order order) {
        if (order.getId() == null) throw new ServiceException("審核失敗，訂單ID不能為空");
        if (order.getBelongNoArray() == null || order.getBelongNoArray().length == 0)
            throw new ServiceException("審核失敗，分配編號不能為空");
        if (order.getStatus() == null || !GlobalConstants.OrderStatus.validAuditStatus(order.getStatus()))
            throw new ServiceException("審核失敗，分配編號不能為空");
        Order orderInfo = orderMapper.get(order.getId());
        if (orderInfo == null) throw new ServiceException("審核失敗，訂單不存在");
        if (orderInfo.getStatus().equals(GlobalConstants.OrderStatus.PAYMENT))
            throw new ServiceException("審核失敗，該訂單已審核");
        //服務列表
        List<BoxService> boxServiceList = boxServiceService.getBoxServiceListByOrderId(order.getId());
        if (CollectionUtils.isNotEmpty(boxServiceList)) {
            for (int i = 0; i < boxServiceList.size(); i++) {
                BoxService boxService = boxServiceList.get(i);
                //审核不通过，则取消订单
                if (GlobalConstants.OrderStatus.CANCEL.equals(order.getStatus())) {
                    boxService.setStatus(GlobalConstants.ServiceStatus.INVALID);
                    this.boxServiceService.update(boxService);
                    this.boxRecordService.deleteByServiceId(boxService.getId());
                }
                //订单生效
                else {
                    //分配编号
                    boxService.setBelongNo(order.getBelongNoArray()[i]);
                    this.boxServiceService.update(boxService);
                    //消息推送
                    User user = this.accountService.getUser(boxService.getUserId());
                    if (user != null) {
                        //推送消息订单购买成功
                        String content = MessageFormat.format(GlobalConstants.JPushMsgTemplate.order_success_message,
                                boxService.getProductName(), boxService.getScaleName());
                        JPush.pushMessageToApp(user.getLoginName(), "", content);
                        //定时推送消息
                        final List<BoxRecord> boxRecordList = this.boxRecordService.getBoxRecordList(boxService.getId());
                        if (CollectionUtils.isNotEmpty(boxRecordList)) {
                            //前天16.30通知
                            String beforeDayNoticeTime = DateUtils.getBeforeDayPM1630(boxRecordList.get(0).getAppointmentTime());
                            if (StringUtils.isNotBlank(beforeDayNoticeTime)) {
                                JPush.pushScheduleMessageToApp(user.getLoginName(), "",
                                        MessageFormat.format(GlobalConstants.JPushMsgTemplate.schedule_message,
                                                boxService.getProductName(), "明天"), beforeDayNoticeTime);
                            }
                            //当前天9.00通知
                            String currentDayNoticeTime = DateUtils.getCurrentDayAM900(boxRecordList.get(0).getAppointmentTime());
                            if (StringUtils.isNotBlank(currentDayNoticeTime)) {
                                JPush.pushScheduleMessageToApp(user.getLoginName(), "",
                                        MessageFormat.format(GlobalConstants.JPushMsgTemplate.schedule_message,
                                                boxService.getProductName(), "今天"), currentDayNoticeTime);
                            }
                        }
                    }
                }
            }
        }

        //更新訂單
        orderInfo.setEffectiveTime(new Date());
        orderInfo.setStatus(order.getStatus());
        orderInfo.setRemark(order.getRemark());
        this.orderMapper.update(orderInfo);
    }

}