package com.magicli.security.exceptions;

import org.apache.shiro.authc.AuthenticationException;

public class CaptchaCodeAuditFailException extends AuthenticationException {
    public CaptchaCodeAuditFailException(String message) {
        super(message);
    }
}
