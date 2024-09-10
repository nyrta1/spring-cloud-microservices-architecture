package com.nyrta1.minio_file_server.annotation.minio;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MinioPermissionAspect {
    @Before("@annotation(minioPermission) && args(bucketName,..)")
    public void checkPermission(MinioPermission minioPermission, String bucketName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User is not authenticated");
        }

        String requiredRole = "ROLE_MINIO_BUCKET_" + bucketName.toUpperCase() + "_" + minioPermission.permission().name() + "_ACCESS";

        if (!hasAccess(authentication, requiredRole)) {
            throw new SecurityException("User does not have the required permission: " + requiredRole);
        }
    }

    private boolean hasAccess(Authentication authentication, String requiredRole) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(requiredRole));
    }
}
