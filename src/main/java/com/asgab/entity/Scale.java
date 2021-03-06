package com.asgab.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 产品规格
 */
public class Scale {

    private Long id;
    private Long productId; //产品ID
    private String scale; //规格
    private Integer needQuote; //需要具体报价
    private BigDecimal twelveMonthPrice; //12月单价
    private BigDecimal sixMonthPrice; //6月单价
    private BigDecimal threeMonthPrice; //3月单价
    private BigDecimal oneMonthPrice; //1月单价
    private Integer num; //库存
    private Integer status; //状态
    private Date createTime; //创建时间
    private Date updateTime; //更新时间

    public Scale() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public Integer getNeedQuote() {
        return needQuote;
    }

    public void setNeedQuote(Integer needQuote) {
        this.needQuote = needQuote;
    }

    public BigDecimal getTwelveMonthPrice() {
        return twelveMonthPrice;
    }

    public void setTwelveMonthPrice(BigDecimal twelveMonthPrice) {
        this.twelveMonthPrice = twelveMonthPrice;
    }

    public BigDecimal getSixMonthPrice() {
        return sixMonthPrice;
    }

    public void setSixMonthPrice(BigDecimal sixMonthPrice) {
        this.sixMonthPrice = sixMonthPrice;
    }

    public BigDecimal getThreeMonthPrice() {
        return threeMonthPrice;
    }

    public void setThreeMonthPrice(BigDecimal threeMonthPrice) {
        this.threeMonthPrice = threeMonthPrice;
    }

    public BigDecimal getOneMonthPrice() {
        return oneMonthPrice;
    }

    public void setOneMonthPrice(BigDecimal oneMonthPrice) {
        this.oneMonthPrice = oneMonthPrice;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean validCycle(Integer cycle) {
        boolean flag = false;
        switch (cycle) {
            case 1:
                flag = oneMonthPrice.doubleValue() != 0;
                break;
            case 2:
                flag = threeMonthPrice.doubleValue() != 0;
                break;
            case 3:
                flag = sixMonthPrice.doubleValue() != 0;
                break;
            case 4:
                flag = twelveMonthPrice.doubleValue() != 0;
                break;
            default:
                break;
        }
        return flag;
    }

    public boolean validTotalPrice(Integer cycle, BigDecimal totalPrice, BigDecimal appointFee, Integer quantity) {
        boolean flag = false;
        switch (cycle) {
            case 1:
                flag = oneMonthPrice.multiply(new BigDecimal(quantity)).add(appointFee).doubleValue() == totalPrice.doubleValue();
                break;
            case 2:
                flag = threeMonthPrice.multiply(new BigDecimal(3)).multiply(new BigDecimal(quantity)).add(appointFee).doubleValue() == totalPrice.doubleValue();
                break;
            case 3:
                flag = sixMonthPrice.multiply(new BigDecimal(6)).multiply(new BigDecimal(quantity)).add(appointFee).doubleValue() == totalPrice.doubleValue();
                break;
            case 4:
                flag = twelveMonthPrice.multiply(new BigDecimal(12)).multiply(new BigDecimal(quantity)).add(appointFee).doubleValue() == totalPrice.doubleValue();
                break;
            default:
                break;
        }
        return flag;
    }
}