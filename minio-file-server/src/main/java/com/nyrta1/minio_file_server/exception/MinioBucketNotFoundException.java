package com.nyrta1.minio_file_server.exception;

public class MinioBucketNotFoundException extends RuntimeException {
    public MinioBucketNotFoundException() {
    }

    public MinioBucketNotFoundException(String message) {
        super(message);
    }

    public MinioBucketNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinioBucketNotFoundException(Throwable cause) {
        super(cause);
    }

    public MinioBucketNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
