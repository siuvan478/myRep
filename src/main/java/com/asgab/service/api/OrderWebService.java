package com.asgab.service.api;


import com.alibaba.druid.support.json.JSONUtils;
import com.asgab.constants.GlobalConstants;
import com.asgab.entity.BoxService;
import com.asgab.entity.Order;
import com.asgab.entity.Product;
import com.asgab.entity.Scale;
import com.asgab.repository.BoxServiceMapper;
import com.asgab.repository.OrderMapper;
import com.asgab.service.AddressService;
import com.asgab.service.ApiException;
import com.asgab.service.ProductService;
import com.asgab.service.ScaleService;
import com.asgab.util.BeanMapper;
import com.asgab.util.LoginUtil;
import com.asgab.web.api.param.OrderBuyParam;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;

@Component
@Transactional
public class OrderWebService {

    private static Logger logger = Logger.getLogger(OrderWebService.class);

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private BoxServiceMapper boxServiceMapper;

    @Resource
    private AddressService addressService;

    @Resource
    private ProductService productService;

    @Resource
    private ScaleService scaleService;

    public void submitOrder(OrderBuyParam orderBuyParam) {
        Long userId = LoginUtil.getUserId();
        if (userId == null) {
            throw new ApiException("用户未登录");
        }
        if (orderBuyParam.getProductId() == null || orderBuyParam.getScaleId() == null || orderBuyParam.getCycle() == null
                || orderBuyParam.getTotalPrice() == null || orderBuyParam.getTotalPrice().doubleValue() <= 0
                || !GlobalConstants.CycleEnum.validCycle(orderBuyParam.getCycle())) {
            throw new ApiException("订单参数异常");
        }
        Product productInfo = productService.getProductFromCache(orderBuyParam.getProductId());
        if (productInfo == null) {
            logger.error(MessageFormat.format("购买的服务已下架，productId={0}不存在", orderBuyParam.getProductId()));
            throw new ApiException("购买的服务已下架");
        }
        Scale scaleInfo = scaleService.getScaleFromCache(orderBuyParam.getScaleId());
        if (scaleInfo == null) {
            logger.error(MessageFormat.format("购买的服务已下架，scaleId={0}不存在", orderBuyParam.getScaleId()));
            throw new ApiException("购买的服务已下架");
        }
        if (!scaleInfo.getProductId().equals(orderBuyParam.getProductId())) {
            logger.error(MessageFormat.format("购买的服务已下架，[orderInfo={0}/{1}，scaleInfo={3}/{4}]",
                    orderBuyParam.getScaleId(), orderBuyParam.getProductId(), scaleInfo.getId(), scaleInfo.getProductId()));
            throw new ApiException("购买的服务已下架");
        }
        if (!scaleInfo.validCycle(orderBuyParam.getCycle())) {
            logger.error(MessageFormat.format("不支持当前租用时间, cycle={0}", orderBuyParam.getCycle()));
            throw new ApiException("不支持当前租用时间");
        }
        if (!scaleInfo.validTotalPrice(orderBuyParam.getCycle(), orderBuyParam.getTotalPrice())) {
            logger.error(MessageFormat.format("购买的服务已下架, cycle={0}, totalPrice={1}, productInfo=[\r\n{0}\r\n]",
                    orderBuyParam.getCycle(), orderBuyParam.getTotalPrice(), JSONUtils.toJSONString(scaleInfo)));
            throw new ApiException("购买的服务已下架");
        }

        //预服务
        BoxService service = BeanMapper.map(orderBuyParam, BoxService.class);
        service.setUserId(userId);
        Long serviceId = boxServiceMapper.save(service);

        //构建订单
        Order order = BeanMapper.map(orderBuyParam, Order.class);
        order.setUserId(userId);
        order.setOrderNo("TODO-ORDER-NO-01");
        order.setCallbackId(serviceId);
        orderMapper.save(order);

    }
}