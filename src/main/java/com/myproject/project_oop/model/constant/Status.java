package com.myproject.project_oop.model.constant;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Status {
    VERIFIED,
    NOT_VERIFIED;

    public SimpleGrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }

}