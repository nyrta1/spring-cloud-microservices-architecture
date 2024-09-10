package com.nyrta1.minio_file_server.services.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    public UUID getUserID() {
        Map<String, Object> attributes = getUserAttributes();
        String id = (String) attributes.get("sub");
        return UUID.fromString(id);
    }

    private Map<String, Object> getUserAttributes() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaims();
    }
}
