package com.manmeet.moudgill.NewTodoApplication.exceptions;

public class ApplicationException extends RuntimeException {
    private final String customMessage;

    public ApplicationException(String customMessage) {
        super(customMessage);
        this.customMessage = customMessage;
    }


    public String getMessage() {
        return this.customMessage;
    }

    public String toString() {
        return this.customMessage;
    }

}
