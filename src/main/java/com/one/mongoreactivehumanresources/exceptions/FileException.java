package com.one.mongoreactivehumanresources.exceptions;

public class FileException extends RuntimeException {
    public static final String DESCRIPTION = "File exception";

    public FileException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
