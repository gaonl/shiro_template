package com.magicli.security;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;


public class MagicliHttpHeaderSessionManager extends DefaultWebSessionManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(MagicliHttpHeaderSessionManager.class);

    @Override
    public Serializable getSessionId(ServletRequest request, ServletResponse response) {


        Serializable id = WebUtils.toHttp(request).getHeader(AllInOnePasswordToken.TOKEN_KEY);

        if (StringUtils.isEmpty(id)) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (AllInOnePasswordToken.TOKEN_KEY.equals(cookie.getName())) {
                        id = cookie.getValue();
                        break;
                    }
                }
            }
        }

        if (StringUtils.isEmpty(id)) {
            id = WebUtils.toHttp(request).getParameter(AllInOnePasswordToken.TOKEN_KEY);
        }

        if (StringUtils.isEmpty(id)) {
            id = super.getSessionId(request, response);
        }

        if (StringUtils.isEmpty(id)) {
            LOGGER.info("获取不到x-auth-token, 请求地址：" + WebUtils.toHttp(request).getRequestURI());
        }

        return id;
    }

    protected void onStart(Session session, SessionContext context) {
        if (!WebUtils.isHttp(context)) {
            LOGGER.debug("SessionContext argument is not HTTP compatible or does not have an HTTP request/response pair. No session ID cookie will be set.");
        } else {
            HttpServletRequest request = WebUtils.getHttpRequest(context);
            HttpServletResponse response = WebUtils.getHttpResponse(context);

            Serializable sessionId = session.getId();

            response.addHeader(AllInOnePasswordToken.TOKEN_KEY, String.valueOf(sessionId));
        }

    }
}
