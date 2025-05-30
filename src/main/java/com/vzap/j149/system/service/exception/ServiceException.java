package com.vzap.j149.system.service.exception;

/**
 * Exception thrown when there's an error in service operations.
 */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}