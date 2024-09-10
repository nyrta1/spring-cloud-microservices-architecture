package com.nyrta1.minio_file_server.exception;

public class MinioBucketAlreadyExistsException extends RuntimeException {
    public MinioBucketAlreadyExistsException() {
    }

    public MinioBucketAlreadyExistsException(String message) {
        super(message);
    }

    public MinioBucketAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinioBucketAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public MinioBucketAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
