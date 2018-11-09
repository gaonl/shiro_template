package com.magicli.shiro;

import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.FactoryBean;

public class MyShiroFilterFactoryBean implements FactoryBean {
    private WebSecurityManager webSecurityManager;
    private FilterChainResolver filterChainResolver;

    public void setWebSecurityManager(WebSecurityManager webSecurityManager) {
        this.webSecurityManager = webSecurityManager;
    }

    public void setFilterChainResolver(FilterChainResolver filterChainResolver) {
        this.filterChainResolver = filterChainResolver;
    }

    @Override
    public Object getObject() throws Exception {
        return new MyShiroFilter(webSecurityManager, filterChainResolver);
    }

    @Override
    public Class<?> getObjectType() {
        return MyShiroFilter.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    private static final class MyShiroFilter extends AbstractShiroFilter {

        protected MyShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
            super();
            if (webSecurityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            }
            setSecurityManager(webSecurityManager);
            if (resolver != null) {
                setFilterChainResolver(resolver);
            }
        }
    }
}
