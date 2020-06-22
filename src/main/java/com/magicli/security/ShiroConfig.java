package com.magicli.security;


import com.magicli.security.session.MagicliRedisSessionDao;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    /**
     * 配置 过滤器
     *
     * @param securityManager shiro安全管理器
     * @return 过滤器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(org.apache.shiro.mgt.SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //注意过滤器配置顺序 不能颠倒
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/sysLogin", "anon");
        filterChainDefinitionMap.put("/sendCaptcha", "anon");
        filterChainDefinitionMap.put("/wechat/weChatAuth/**", "anon");
        filterChainDefinitionMap.put("/wechat/user/registerBusiness", "anon");
        filterChainDefinitionMap.put("/wechat/user/registerEmployee", "anon");
        filterChainDefinitionMap.put("/wechat/user/getCacheCaptcha", "anon");
        filterChainDefinitionMap.put("/api/wechat/WeChatEvent/**", "anon");
        filterChainDefinitionMap.put("/wechat/smallVideo/**", "anon");
        filterChainDefinitionMap.put("/wechat/weChatRecord/**", "anon");
        filterChainDefinitionMap.put("/mq/rocketMqTest/**", "anon");
        filterChainDefinitionMap.put("/wechat/orderPay/**", "anon");
        filterChainDefinitionMap.put("/wechat/pay/**", "anon");
        filterChainDefinitionMap.put("/wechat/product/getPoster", "anon");
        filterChainDefinitionMap.put("/wechat/product/getProductShareInfo", "anon");
        filterChainDefinitionMap.put("/a/manage/findModuleList", "anon");
        filterChainDefinitionMap.put("/a/order/wxBuy", "anon");

        //拦截其他所有接口
        filterChainDefinitionMap.put("/**", "heygoodAuthFilter");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        /**
         * 自定义的filter
         */
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("heygoodAuthFilter", new MagicliAuthFilter());
        shiroFilterFactoryBean.setFilters(filters);

        return shiroFilterFactoryBean;
    }

    /**
     * 配置鉴权数据源
     *
     * @return 数据源
     */
    @Bean
    public AllInOneSecurityRealm allInOneSecurityRealm() {
        AllInOneSecurityRealm allInOneSecurityRealm = new AllInOneSecurityRealm();
        return allInOneSecurityRealm;
    }

    /**
     * 配置 shiro安全管理器
     *
     * @return 安全管理器
     */
    @Bean
    public org.apache.shiro.mgt.SecurityManager securityManager() {

        CacheManager cacheManager = new MemoryConstrainedCacheManager();

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setCacheManager(cacheManager);

        MagicliHttpHeaderSessionManager sessionManager = new MagicliHttpHeaderSessionManager();
        //session过期扫描时间间隔6个小时
        sessionManager.setSessionValidationInterval(6 * 3600000L);
        sessionManager.setSessionDAO(sessionDao());

        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(allInOneSecurityRealm());
        return securityManager;
    }

    /**
     * 配置 sessionDao
     *
     * @return 安全管理器
     */
    @Bean
    public SessionDAO sessionDao() {
//        MagicliMemorySessionDao sessionDao = new MagicliMemorySessionDao();
        MagicliRedisSessionDao sessionDao = new MagicliRedisSessionDao();
        return sessionDao;
    }
}
