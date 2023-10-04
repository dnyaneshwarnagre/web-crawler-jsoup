package com.rss.feed.controller;


import com.rss.feed.dto.response.Response;
import com.rss.feed.dto.response.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public class Controller {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HttpServletRequest httpServletRequest;

    public ResponseEntity<Response> data(Object entity) {
        return new ResponseEntity<>(Response.builder().status(HttpStatus.OK.value()).code(ResponseCode.ENTITY)
                .message(entity).path(httpServletRequest.getRequestURI()).requestId(UUID.randomUUID().toString())
                .version("1.0").build(), HttpStatus.OK);
    }
}
