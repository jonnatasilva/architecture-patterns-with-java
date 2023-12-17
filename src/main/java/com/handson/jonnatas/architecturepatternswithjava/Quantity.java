package com.handson.jonnatas.architecturepatternswithjava;

public record Quantity(int value) {

    public static Quantity of(int value) {
        return new Quantity(value);
    }
}
