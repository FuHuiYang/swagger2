package com.yang.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * author : yangfuhui
 * Project: swagger2
 * Package com.yang.condition
 * Description:
 * date Date : 2019年08月22日 14:19
 */
public class NoodlesCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        return conditionContext.getEnvironment().getProperty("people").equals("北方人");
    }
}
