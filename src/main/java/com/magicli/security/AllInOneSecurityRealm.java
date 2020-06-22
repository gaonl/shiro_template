package com.magicli.security;


import com.magicli.security.captcha.CaptchaDao;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;


public class AllInOneSecurityRealm extends AuthorizingRealm {

    public boolean supports(AuthenticationToken token) {
        return token instanceof AllInOnePasswordToken;
    }

    @Autowired
    CaptchaDao captchaDao;

    /**
     * 身份
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        /**
         * 根据login的token，取得鉴权信息
         */
        AllInOnePasswordToken allInOnePasswordToken = (AllInOnePasswordToken) authenticationToken;

        return new SimpleAuthenticationInfo(new AllInOneSysUserInfo(null, null, null), null, getName());
    }

    /**
     * 权限
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        AllInOneSysUserInfo allInOneSysUserInfo = (AllInOneSysUserInfo) principalCollection.getPrimaryPrincipal();

        Set<String> roles = new HashSet<>();
        roles.add("admin");

        return new SimpleAuthorizationInfo(roles);

    }

}
