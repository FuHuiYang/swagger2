package com.yang.quartz;

import java.util.Date;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.quaartz
 * @Description: TODO
 * @date Date : 2019年08月27日 14:42
 */
public class HelloServiceA {
    public void sayHello() {
        System.out.println("MyJob2-hello service >>>" + new Date());
    }
}
