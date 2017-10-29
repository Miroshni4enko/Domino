package com.vimi.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 *
 */
public class DataBaseException extends Exception {
    private static final Logger LOG = LoggerFactory.getLogger(DataBaseException.class);

    private String message;

    public DataBaseException(String message, Exception e){
        super(message,e);
        this.message = message;
        LOG.error(Arrays.toString(e.getStackTrace()).replaceAll(" ", "\t\n"));
    }

    @Override
    public String getMessage() {
        return super.getMessage() + "is: " + message;
    }
}
