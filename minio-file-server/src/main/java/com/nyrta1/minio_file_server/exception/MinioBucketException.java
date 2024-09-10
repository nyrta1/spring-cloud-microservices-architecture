package com.nyrta1.minio_file_server.exception;

public class MinioBucketException extends RuntimeException {
    public MinioBucketException() {
    }

    public MinioBucketException(String message) {
        super(message);
    }

    public MinioBucketException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinioBucketException(Throwable cause) {
        super(cause);
    }

    public MinioBucketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
