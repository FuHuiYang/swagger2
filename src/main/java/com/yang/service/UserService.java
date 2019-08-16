package com.yang.service;

import com.yang.bean.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.service
 * @Description: TODO
 * @date Date : 2019年08月16日 10:32
 */
@Service
@CacheConfig(cacheNames = "c1")
public class UserService {
    @Cacheable(key = "#id")
    public  User getUserById(Integer id , String username){
        System.out.println("getUserById");
        User user = new User();
        user.setId(id);
        user.setUserName(username);
        return user;
    };
    @CachePut(key = "#user.id")
    public  User updateUserById(User user){
        return user;
    };
    @CacheEvict
    public  void delUserById(Integer id ){
        System.out.println("删除成功");
    };
}
