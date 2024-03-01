package com.company.searchstore.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage {

    private String errorMessage;
    private String errorCode;
}
