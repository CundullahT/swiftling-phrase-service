package com.swiftling.enums;

import com.swiftling.exception.UnknownStatusException;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Status {

    IN_PROGRESS("In Progress"), LEARNED("Learned");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    private static final Map<String,Status> BY_VALUE =
            Stream.of(values())
                    .collect(Collectors.toMap(Status::getValue, s -> s));

    public static Status findByValue(String value) {
        Status status = BY_VALUE.get(value);
        if (status == null) {
            throw new UnknownStatusException("Unknown Status: " + value);
        }
        return status;
    }

}
