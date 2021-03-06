package com.polopoly.ps.layout.element.util;

public class InvalidControllerPolicyException extends RuntimeException {

    public InvalidControllerPolicyException() {
    }

    public InvalidControllerPolicyException(String message) {
        super(message);
    }

    public InvalidControllerPolicyException(Throwable cause) {
        super(cause);
    }

    public InvalidControllerPolicyException(String message, Throwable cause) {
        super(message, cause);
    }

}
