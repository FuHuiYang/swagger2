package com.yang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.controller
 * @Description: TODO
 * @date Date : 2019年10月11日 10:19
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
