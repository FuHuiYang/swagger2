package com.yang.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.runner
 * @Description: TODO
 * @date Date : 2019年08月26日 13:56
 */
@Component
@Order(100)
public class MyCommandLineRunner1 implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

    }
}
