package com.asgab.entity;

/**
 * 功能：
 * 作者：Siuvan(Siuvan@lianj.com)
 * 日期：2017年09月30日 上午 9:40
 * 版权所有：广东联结网络技术有限公司 版权所有(C) 2016-2018
 */
public class City {

    private Long id;
    private String name;
    private String nameEN;
    private Integer status;

    public City() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}