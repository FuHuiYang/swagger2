package com.yang.bean;
import java.math.BigDecimal;
import java.util.Date;

import java.math.BigDecimal;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.bean
 * @Description: TODO
 * @date Date : 2020年03月17日 16:19
 */


public class SZIndex {
    private Integer id;

    private String code;

    private String codename;

    private BigDecimal closePrice;

    private BigDecimal maxPrice;

    private BigDecimal minPrice;

    private BigDecimal startPrice;

    private BigDecimal startOnedayPrice;

    private BigDecimal quota;

    private BigDecimal rate;

    private BigDecimal volume;

    private BigDecimal amount;

    private Date closeTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename == null ? null : codename.trim();
    }

    public BigDecimal getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(BigDecimal closePrice) {
        this.closePrice = closePrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getStartOnedayPrice() {
        return startOnedayPrice;
    }

    public void setStartOnedayPrice(BigDecimal startOnedayPrice) {
        this.startOnedayPrice = startOnedayPrice;
    }

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }
}
