package com.yang.controller;

import com.yang.bean.RespBean;
import com.yang.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.controller
 * @Description: TODO
 * @date Date : 2019年08月15日 9:41
 * http://localhost:8080/swagger-ui.html
 */
@RestController
@Api(tags = "用户管理相关接口")
@RequestMapping("/user")
public class UserController {

    @PostMapping("/add")
    @ApiOperation("添加用户接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName",value = "用户名称",defaultValue = "张三"),
            @ApiImplicitParam(name = "adress",value = "用户地址",defaultValue = "北京",required = true)
    })
    public String addUser(String userName, @RequestParam(required = true) String adress){
        return "添加成功";
    }

    @GetMapping("/select")
    @ApiOperation("根据用户id查询用户接口")
    @ApiImplicitParam(name = "id",value = "用户id",defaultValue = "99",required = true,type = "integer")
    public User selectUserById(Integer id){
        User user =new User();
        user.setId(id);
        return user;
    }

    @PutMapping("/update")
    @ApiOperation("根据id更新用户")
    public User updateUserById(@RequestBody User user){
        return user;
    }
    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @ApiOperation(value="删除用户", notes="根据url的id来指定删除用户")
    @ApiImplicitParam(name = "id", value = "用户ID",dataType = "integer", paramType = "path")
    @RequestMapping(value = "del/{id}", method = RequestMethod.DELETE)
    public Integer delete (@PathVariable(value = "id") Integer id){
       User user =new User();
       user.setId(id);
        return user.getId();
    }

}
