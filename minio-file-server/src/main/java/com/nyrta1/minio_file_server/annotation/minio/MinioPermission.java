package com.nyrta1.minio_file_server.annotation.minio;

import com.nyrta1.minio_file_server.enums.MinioAccessPermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MinioPermission {
    MinioAccessPermission permission();
}

