package com.swiftling.enums;

public enum Status {

    IN_PROGRESS("In Progress"), LEARNED("Learned");

    private final String value;

    Status(String value) {
        this.value = value;
    }

}
