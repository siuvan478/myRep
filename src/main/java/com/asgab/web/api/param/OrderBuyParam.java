package com.asgab.web.api.param;


import java.io.Serializable;
import java.math.BigDecimal;

public class OrderBuyParam implements Serializable {

    private static final long serialVersionUID = -939450214400403165L;
    private Long productId;
    private Long addressId;
    private Long scaleId;
    private Integer cycle;
    private BigDecimal totalPrice;

    public OrderBuyParam() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getScaleId() {
        return scaleId;
    }

    public void setScaleId(Long scaleId) {
        this.scaleId = scaleId;
    }

    public Integer getCycle() {
        return cycle;
    }

    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

}