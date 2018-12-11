package com.magicli.shiro;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.util.Destroyable;

public class MyLifecycleBeanPostProcessor extends LifecycleBeanPostProcessor {

    @Override
    public boolean requiresDestruction(Object bean) {
        if (bean instanceof Destroyable) {
            return true;
        }
        return false;
    }
}
