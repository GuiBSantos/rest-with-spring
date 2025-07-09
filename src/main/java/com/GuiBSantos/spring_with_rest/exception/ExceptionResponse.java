package com.GuiBSantos.spring_with_rest.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {

}
