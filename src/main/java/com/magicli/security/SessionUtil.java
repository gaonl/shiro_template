package com.magicli.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class SessionUtil {
    public static final String SESSION_KEY = "info";

    private SessionUtil() {
    }

    public static final Session getSession() {
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            return null;
        }
        Session session = subject.getSession(false);

        return session;
    }

    public static final AllInOneSysUserInfo getAllInfo() {
        Session session = getSession();
        if (session != null) {
            AllInOneSysUserInfo allInOneSysUserInfo = (AllInOneSysUserInfo) session.getAttribute(SESSION_KEY);
            return allInOneSysUserInfo;
        }

        return null;
    }
}
