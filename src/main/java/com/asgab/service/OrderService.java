package com.asgab.service;

import com.asgab.constants.GlobalConstants;
import com.asgab.core.pagination.Page;
import com.asgab.entity.*;
import com.asgab.repository.OrderMapper;
import com.asgab.util.Collections3;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class OrderService {

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
        return orderMapper.get(id);
    }

    public void update(Order product) {
        orderMapper.update(product);
    }

    public void audit(Order order) {
        if (order.getId() == null) {
            throw new ServiceException("审核失败，订单ID不能为空");
        }
        if (order.getStatus() == null || GlobalConstants.OrderStatus.validAuditStatus(order.getStatus())) {
            throw new ServiceException("审核失败，订单ID不能为空");
        }
        Order orderInfo = orderMapper.get(order.getId());
        if (orderInfo == null) {
            throw new ServiceException("审核失败，订单不存在");
        }
        //审核不通过，则取消订单
        if (GlobalConstants.OrderStatus.CANCEL.equals(order.getStatus())) {
            //文件服务|记录取消
            BoxService boxService = boxServiceService.get(orderInfo.getCallbackId());
            if (boxService != null) {
                boxService.setStatus(GlobalConstants.ServiceStatus.INVALID);
                this.boxServiceService.update(boxService);
                this.boxRecordService.deleteByServiceId(boxService.getId());
            }
        }
        //订单生效
        else {
            orderInfo.setEffectiveTime(new Date());
        }
        orderInfo.setStatus(order.getStatus());
        orderInfo.setRemark(order.getRemark());
        orderMapper.update(orderInfo);
    }

}