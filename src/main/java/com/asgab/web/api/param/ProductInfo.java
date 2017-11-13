package com.asgab.web.api.param;

import com.asgab.entity.Scale;

import java.io.Serializable;
import java.util.List;


public class ProductInfo implements Serializable {

    private static final long serialVersionUID = 2355250460186538414L;
    private Long id;
    private String productName;//名称
    private String productNo;//编号
    private String feature;//特征
    private String description;//描述
    private String image;//图片
    private String imageDetail;//详情图片
    private String imageList;//详情图片
    private Integer isOwned = 0;//是否拥有该产品服务
    private List<Scale> scales;

    public ProductInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageDetail() {
        return imageDetail;
    }

    public void setImageDetail(String imageDetail) {
        this.imageDetail = imageDetail;
    }

    public String getImageList() {
        return imageList;
    }

    public void setImageList(String imageList) {
        this.imageList = imageList;
    }

    public Integer getIsOwned() {
        return isOwned;
    }

    public void setIsOwned(Integer isOwned) {
        this.isOwned = isOwned;
    }

    public List<Scale> getScales() {
        return scales;
    }

    public void setScales(List<Scale> scales) {
        this.scales = scales;
    }
}