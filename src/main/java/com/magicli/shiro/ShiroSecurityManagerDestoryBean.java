package com.magicli.shiro;

import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by gaonl on 2018/11/12.
 */
public class ShiroSecurityManagerDestoryBean implements ApplicationContextAware {

    ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    //在容器关闭时，调用destroy方法，销毁DefaultWebSecurityManager，调用各种实现了Destroyable的组件，销毁各种资源
    //其中一个就是如果使用到了EhCacheManager就会调用EhCacheManager，就会关闭底层的EhCache 的 cacheManager，不然会存在关不掉的线程，导致容器关不掉
    public void destroy() {
        Object securityManager = applicationContext.getBean("webSecurityManager");
        if (securityManager == null || !(securityManager instanceof DefaultWebSecurityManager)) {
            return;
        }
        DefaultWebSecurityManager defaultWebSecurityManager = (DefaultWebSecurityManager) securityManager;
        defaultWebSecurityManager.destroy();
    }
}
