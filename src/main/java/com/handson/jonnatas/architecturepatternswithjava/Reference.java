package com.handson.jonnatas.architecturepatternswithjava;

public record Reference(String value) {

    public static Reference of(String value) {
        return new Reference(value);
    }
}
