package com.magicli.filters;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by gaonl on 2018/9/17.
 */
public class HasAnyRolesAuthorizationFilter extends AuthorizationFilter {

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        Subject subject = this.getSubject(request, response);
        String[] rolesArray = ((String[]) mappedValue);
        if (rolesArray != null && rolesArray.length != 0) {
            List<String> roles = CollectionUtils.asList(rolesArray);
            boolean[] hasRoles = subject.hasRoles(roles);

            for (boolean b : hasRoles) {
                if (b) {
                    return b;
                }
            }

            return false;
        } else {
            return true;
        }
    }

}
