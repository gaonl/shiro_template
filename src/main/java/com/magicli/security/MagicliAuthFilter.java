package com.magicli.security;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MagicliAuthFilter extends AccessControlFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        WebUtils.toHttp(servletResponse).addHeader("Content-Type", "application/json");
        String resultResp = "鉴权失败";
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.getWriter().write(resultResp);

        return false;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = this.getSubject(servletRequest, servletResponse);
        return subject.isAuthenticated();
    }

}
