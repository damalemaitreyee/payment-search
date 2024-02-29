package com.company.searchstore.exception;

public class GeneralPaymentsException extends RuntimeException {

    public GeneralPaymentsException(String message) {
        super(message);
    }

    public GeneralPaymentsException(Exception e, String errorResponse) {
        super(errorResponse, e);
    }

}
