package com.rss.feed.exception;

import com.rss.feed.config.AppConfig;
import com.rss.feed.dto.response.Response;
import com.rss.feed.dto.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.UUID;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    private static final HttpHeaders httpHeaders = new HttpHeaders();

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception exception, WebRequest request) {
        log.error("Generic Exception", exception);
        return handleExceptionInternal(exception, buildResponse(ResponseCode.INTERNAL_ERROR, exception.getMessage(), request),
                httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    private Response buildResponse(ResponseCode code, String message, WebRequest request) {
        return Response.builder()
                .code(code)
                .message(message)
                .path(request.getContextPath())
                .requestId(UUID.randomUUID().toString())
                .errors(null)
                .version(AppConfig.getVersion())
                .build();
    }
}