package com.marinoricardo.todosimple.exceptions;

import java.util.List;



public class ErrorResponse {
    private final int status;
    private final String message;
    private final String stackTrace;
    private List<ValidationError> errors;

    
    private static class ValidationError {
        private final String field;
        private final String messaage;
    }

    

}
