package com.redfox.lunchmanager.util.exception;

public class DataConflictException extends RuntimeException {
    public DataConflictException(String msg) {
        super(msg);
    }
}