package com.handson.jonnatas.architecturepatternswithjava.domain;

public record Quantity(int value) {

    public static Quantity TEN = new Quantity(10);
    public static Quantity A_HUNDRED = new Quantity(100);

    public static Quantity of(int value) {
        return new Quantity(value);
    }
}
