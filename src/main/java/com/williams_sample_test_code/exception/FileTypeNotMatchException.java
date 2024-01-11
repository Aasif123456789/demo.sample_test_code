package com.williams_sample_test_code.exception;


public class FileTypeNotMatchException extends Exception {
    public FileTypeNotMatchException(String ExceptionMessage) {
        super(ExceptionMessage);
    }
}

