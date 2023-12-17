package com.handson.jonnatas.architecturepatternswithjava.domain;

public record Reference(String value) {

    public static Reference of(String value) {
        return new Reference(value);
    }
}
