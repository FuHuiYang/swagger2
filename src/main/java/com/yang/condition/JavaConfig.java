package com.yang.condition;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author : yangfuhui
 * @Project: swagger2
 * @Package com.yang.condition
 * @Description: TODO
 * @date Date : 2019年08月22日 14:21
 */
@Configuration
public class JavaConfig {
    @Bean("food")
//    @Conditional(RiceCondition.class)
    @Profile("南方人")
    Food rice(){
        return  new Rice();
    }
    @Bean("food")
//    @Conditional(NoodlesCondition.class)
    @Profile("北方人")
    Food noodles(){
        return  new Noodles();
    }
}
