package com.yang.condition;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.condition
 * @Description: TODO
 * @date Date : 2019年08月22日 14:24
 */
public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext actx = new AnnotationConfigApplicationContext();
//        actx.getEnvironment().getSystemProperties().put("people","南方人");
        actx.getEnvironment().setActiveProfiles("南方人");
        actx.register(JavaConfig.class);
        actx.refresh();
        Food food = (Food) actx.getBean("food");
        System.out.println(food.showName());

    }
}
