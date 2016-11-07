package com.cxtx.controller;

import com.cxtx.exception.ParameterInvalidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jinchuyang on 16/10/19.
 */
@CrossOrigin
@RequestMapping("/api")
public abstract class ApiController {


    protected void checkParameter(boolean condition, Object errorMessage) {
        if (!condition) {
            throw new ParameterInvalidException(errorMessage);
        }
    }
}
