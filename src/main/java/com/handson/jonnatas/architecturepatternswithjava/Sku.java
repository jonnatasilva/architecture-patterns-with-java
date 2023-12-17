package com.handson.jonnatas.architecturepatternswithjava;

public record Sku(String value) {

    public static Sku of(String value) {
        return new Sku(value);
    }
}
