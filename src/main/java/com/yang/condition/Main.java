package com.yang.condition;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.condition
 * @Description: TODO
 * @date Date : 2019年08月22日 14:24
 */
public class Main {
    public static void main(String[] args) {
        List<String> aa = new ArrayList<String>();
        aa.add("F1");
        aa.add("F2");
        aa.add("F3");
        for (int i=0;i<aa.size();i++) {
            if ("F3".equals(aa.get(i))) {
                aa.remove(aa.get(i));
            }
        }
       for (String temp : aa) {
           if ("F3".equals(temp)) {
               aa.remove(temp);
           }
       }
       for (String temp : aa){
                 System.out.println(temp);
       }
        AnnotationConfigApplicationContext actx = new AnnotationConfigApplicationContext();
//        actx.getEnvironment().getSystemProperties().put("people","南方人");
        actx.getEnvironment().setActiveProfiles("南方人");
        actx.register(JavaConfig.class);
        actx.refresh();
        Food food = (Food) actx.getBean("food");
        System.out.println(food.showName());

    }
}
