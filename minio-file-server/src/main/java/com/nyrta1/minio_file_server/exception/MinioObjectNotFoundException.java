package com.nyrta1.minio_file_server.exception;

public class MinioObjectNotFoundException extends RuntimeException {
    public MinioObjectNotFoundException() {
    }

    public MinioObjectNotFoundException(String message) {
        super(message);
    }

    public MinioObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinioObjectNotFoundException(Throwable cause) {
        super(cause);
    }

    public MinioObjectNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
