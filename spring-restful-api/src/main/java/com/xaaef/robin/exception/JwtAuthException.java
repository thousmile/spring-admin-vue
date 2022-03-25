package com.xaaef.robin.exception;

import com.xaaef.robin.enums.OAuth2Error;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.AuthenticationException;

import static com.xaaef.robin.enums.OAuth2Error.OAUTH2_EXCEPTION;


/**
 * <p>
 * 认证异常
 * </p>
 *
 * @author Wang Chen Chen
 * @version 1.0.1
 * @date 2021/7/5 9:31
 */

@Getter
@Setter
@ToString
public class JwtAuthException extends AuthenticationException {

    private Integer status;


    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }


    public JwtAuthException(int status, String message) {
        super(message);
        this.status = status;
    }


    public JwtAuthException(String message) {
        this(OAUTH2_EXCEPTION.getStatus(), message);
    }


    public JwtAuthException(OAuth2Error status) {
        this(status.getStatus(), status.getError());
    }


    public JwtAuthException() {
        this(OAUTH2_EXCEPTION.getError());
    }


}
