package com.polopoly.pcmd;

import com.polopoly.pcmd.tool.Tool;

/**
 * Throw in {@link Tool#execute(com.polopoly.util.client.PolopolyContext, com.polopoly.pcmd.argument.Parameters)}
 * when a fatal error occurs that should interrupt execution.
 */
public class FatalToolException extends RuntimeException {
    public FatalToolException(Throwable cause) {
        super(cause);
    }

    public FatalToolException(String message) {
        super(message);
    }

    public FatalToolException(String message, Throwable cause) {
        super(message, cause);
    }

}