package com.magicli.security.exceptions;

import org.apache.shiro.authc.AuthenticationException;

public class SysUserAuditFailException extends AuthenticationException {
    public SysUserAuditFailException(String message) {
        super(message);
    }
}
