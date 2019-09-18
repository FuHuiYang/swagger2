package com.yang.controller;

import com.yang.bean.User;
import com.yang.mybatis.mapper.UserMapper;
import com.yang.mybatis.mapper1.UserMapperOne;
import com.yang.mybatis.mapper2.UserMapperTwo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.controller
 * @Description: http://localhost:8080/testdbs/selectUserMapperTwo
 * @date Date : 2019年09月18日 10:44
 */
@RestController
@RequestMapping("/testdbs")
public class UserMapperCotroller {
//    @Autowired
//    UserMapper userMapper;
    @Autowired
    UserMapperOne userMapperOne;
    @Autowired
    UserMapperTwo userMapperTwo;

//    @RequestMapping("/selectUserMapper")
//    public List<User> selectUserMapper(){
//        List<User> userList = userMapper.getAllUsers();
//        return userList;
//    }
    @RequestMapping("/selectUserMapperOne")
    public List<User> selectUserMapperOne(){
        List<User> userList = userMapperOne.getAllUser();
        return userList;
    }
    @RequestMapping("/selectUserMapperTwo")
    public List<User> selectUserMapperTwo(){
        List<User> userList = userMapperTwo.getAllUser();
        return userList;
    }

}
