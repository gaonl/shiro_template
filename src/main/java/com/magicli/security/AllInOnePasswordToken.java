/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/ztech/auto">auto</a> All rights reserved.
 */
package com.magicli.security;


import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 用户和密码（包含验证码）令牌类
 *
 * @author ztech
 * @version 2013-5-19
 */
public class AllInOnePasswordToken extends UsernamePasswordToken {


    public static final String TOKEN_KEY = "x-auth-token";

    private String moduleId;

    private String openid;
    private String unionid;
    private String phone;
    private String captchaCode;

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

	public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCaptchaCode() {
        return captchaCode;
    }

    public void setCaptchaCode(String captchaCode) {
        this.captchaCode = captchaCode;
    }

    public boolean isUsernamePasswordAuth() {
        return StringUtils.isNotBlank(this.getUsername()) && (this.getPassword() != null && this.getPassword().length > 0);
    }
}
