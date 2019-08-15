package com.yang.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.bean
 * @Description: TODO
 * @date Date : 2019年08月15日 9:53
 */
@ApiModel
public class User {
    @ApiModelProperty(value = "用户Id")
    private Integer id;
    @ApiModelProperty(value = "用户姓名")
    private String userName;
    @ApiModelProperty(value = "用户地址")
    private String adress;

    public User(Integer id, String userName, String adress) {
        this.id = id;
        this.userName = userName;
        this.adress = adress;
    }

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
