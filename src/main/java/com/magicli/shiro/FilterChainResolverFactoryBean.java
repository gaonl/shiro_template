package com.magicli.shiro;

import org.apache.shiro.config.Ini;
import org.apache.shiro.util.Factory;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.config.WebIniSecurityManagerFactory;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by gaonl on 2018/11/8.
 */
public class FilterChainResolverFactoryBean implements FactoryBean<FilterChainResolver> {
    @Override
    public FilterChainResolver getObject() throws Exception {

        Factory<FilterChainResolver> factory =
                new IniFilterChainResolverFactory(Ini.fromResourcePath("classpath:shiro.ini"));

        FilterChainResolver filterChainResolver = factory.getInstance();


        return filterChainResolver;
    }

    @Override
    public Class<?> getObjectType() {
        return FilterChainResolver.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
