package com.xaaef.robin.jwt;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * <p>
 *
 * </p>
 *
 * @author WangChenChen
 * @version 1.0
 * @date 2022/3/22 18:05
 */

@AllArgsConstructor
@NoArgsConstructor
public class StringGrantedAuthority implements GrantedAuthority {

    private String authority;

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

}
