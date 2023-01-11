package com.server.datn.server.authority;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class MyPermissionEvaluator implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if ((authentication == null) || (targetDomainObject == null)) {
            return false;
        }
        return hasPrivilege(authentication, targetDomainObject.toString().toUpperCase());
    }


    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if ((authentication == null) || (targetType == null)) {
            return false;
        }
        return hasPrivilege(authentication, targetType.toUpperCase());
    }

    private boolean hasPrivilege(Authentication auth, String targetType) {
        for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
//            if (grantedAuth.getAuthority().startsWith(targetType) && grantedAuth.getAuthority().contains(permission)) {
//                return true;
//            }
            if (grantedAuth.getAuthority().equals(targetType)) {
                return true;
            }
        }
        return false;
    }

}
