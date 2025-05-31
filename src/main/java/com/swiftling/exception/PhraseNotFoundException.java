package com.swiftling.exception;

public class PhraseNotFoundException extends RuntimeException {

    public PhraseNotFoundException(String message) {
        super(message);
    }

}
