package com.magicli.shiro;

import org.apache.shiro.config.Ini;
import org.apache.shiro.util.Factory;
import org.apache.shiro.web.config.WebIniSecurityManagerFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by gaonl on 2018/11/8.
 */
public class WebIniSecurityManagerFactoryBean implements FactoryBean<org.apache.shiro.web.mgt.DefaultWebSecurityManager> {
    @Override
    public org.apache.shiro.web.mgt.DefaultWebSecurityManager getObject() throws Exception {
        Factory<org.apache.shiro.mgt.SecurityManager> factory =
                new WebIniSecurityManagerFactory(Ini.fromResourcePath("classpath:shiro.ini"));

        org.apache.shiro.web.mgt.DefaultWebSecurityManager defaultWebSecurityManager = (org.apache.shiro.web.mgt.DefaultWebSecurityManager)factory.getInstance();


        return defaultWebSecurityManager;
    }

    @Override
    public Class<?> getObjectType() {
        return org.apache.shiro.web.mgt.DefaultWebSecurityManager.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
