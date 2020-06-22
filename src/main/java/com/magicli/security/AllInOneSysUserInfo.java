package com.magicli.security;


import java.io.Serializable;

public class AllInOneSysUserInfo implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

    private String business;
    private String sysUser;
    private String customer;

    public AllInOneSysUserInfo(String business, String sysUser, String customer) {
        this.business = business;
        this.sysUser = sysUser;
        this.customer = customer;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getSysUser() {
        return sysUser;
    }

    public void setSysUser(String sysUser) {
        this.sysUser = sysUser;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
