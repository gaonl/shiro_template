package com.magicli.shiro.factory;

import org.apache.shiro.config.Ini;
import org.apache.shiro.util.Destroyable;
import org.apache.shiro.util.Factory;
import org.apache.shiro.web.config.IniFilterChainResolverFactory;
import org.apache.shiro.web.config.WebIniSecurityManagerFactory;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;


/**
 * Created by gaonl on 2018/11/12.
 */
public class ShiroComponentFromFactoryMethod {

    public static Ini ini = null;
    public static WebSecurityManager webSecurityManager = null;
    public static FilterChainResolver filterChainResolver = null;

    static {
        ini = Ini.fromResourcePath("classpath:shiro-cas.ini");
        Factory<org.apache.shiro.mgt.SecurityManager> factorySecurityManager =
                new WebIniSecurityManagerFactory(ini);
        org.apache.shiro.web.mgt.DefaultWebSecurityManager defaultWebSecurityManager = (org.apache.shiro.web.mgt.DefaultWebSecurityManager) factorySecurityManager.getInstance();
        webSecurityManager = defaultWebSecurityManager;


        Factory<FilterChainResolver> factoryFilterChainResolver =
                new IniFilterChainResolverFactory(ini);
        filterChainResolver = factoryFilterChainResolver.getInstance();

    }


    /**
     * 此处配置的bean，会被在web.xml配置的名为shiroFilter的DelegatingFilterProxy代理，
     * 就是说调用DelegatingFilterProxy的doFilter会调用MyShiroFilter的doFilter
     *
     * @return
     */
    public static final MyShiroFilter getShiroFilter() {
        return new MyShiroFilter(webSecurityManager, filterChainResolver);
    }

    private static final class MyShiroFilter extends AbstractShiroFilter implements Destroyable {

        public WebSecurityManager webSecurityManager = null;
        public FilterChainResolver filterChainResolver = null;

        protected MyShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
            super();
            if (webSecurityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            }
            this.webSecurityManager = webSecurityManager;
            setSecurityManager(webSecurityManager);
            if (resolver != null) {
                this.filterChainResolver = resolver;
                setFilterChainResolver(resolver);
            }
        }

        @Override
        public void destroy() {
            //在容器关闭时，调用destroy方法，销毁DefaultWebSecurityManager，调用各种实现了Destroyable的组件，销毁各种资源
            //其中一个就是如果使用到了EhCacheManager就会调用EhCacheManager，就会关闭底层的EhCache 的 cacheManager，不然会存在关不掉的线程，导致容器关不掉
            super.destroy();
            if (this.webSecurityManager != null && this.webSecurityManager instanceof DefaultWebSecurityManager) {
                ((DefaultWebSecurityManager) this.webSecurityManager).destroy();
            }
        }
    }
}
