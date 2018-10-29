package com.magicli.filters;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created by gaonl on 2018/9/18.
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean isAccessAllowed= super.isAccessAllowed(request, response, mappedValue);

        if(isAccessAllowed){
            Subject subject = getSubject(request, response);
            if(subject.isAuthenticated()){
                request.setAttribute("user",subject.getPrincipals().getPrimaryPrincipal());
            }
        }

        return isAccessAllowed;
    }
}
