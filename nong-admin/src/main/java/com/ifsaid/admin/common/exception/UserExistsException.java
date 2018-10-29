package com.ifsaid.admin.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author: Wang Chen Chen
 * @Date: 2018/10/29 14:43
 * @describe：
 * @version: 1.0
 */
public class UserExistsException extends AuthenticationException {

    public UserExistsException(String msg) {
        super(msg);
    }

}
