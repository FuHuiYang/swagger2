package com.yang.quartz;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.quaartz
 * @Description: TODO
 * @date Date : 2019年08月27日 13:50
 */
@Component
public class MyJob1 {
    public void sayHello() {
        System.out.println("MyJob1>>>"+new Date());
    }
}