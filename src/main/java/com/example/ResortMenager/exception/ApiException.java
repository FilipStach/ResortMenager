package com.example.ResortMenager.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
public class ApiException {
    private final String message;
    private final Integer lineNumber;
    private final String methodName;
    private final String fileName;
    private final HttpStatus httpStatus;
    private final ZonedDateTime zonedDateTime;
    public ApiException(String message,Throwable throwable, HttpStatus httpStatus, ZonedDateTime zonedDateTime) {
        StackTraceElement stackTraceElement = throwable.getStackTrace()[0];
        this.message = message;
        this.lineNumber = stackTraceElement.getLineNumber();
        this.methodName = stackTraceElement.getMethodName();
        this.fileName = stackTraceElement.getFileName();
        this.httpStatus = httpStatus;
        this.zonedDateTime = zonedDateTime;
    }
}

