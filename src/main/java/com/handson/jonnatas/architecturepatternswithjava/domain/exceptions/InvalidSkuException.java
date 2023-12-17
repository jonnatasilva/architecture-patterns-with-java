package com.handson.jonnatas.architecturepatternswithjava.domain.exceptions;

import com.handson.jonnatas.architecturepatternswithjava.domain.Sku;

public class InvalidSkuException extends RuntimeException {

    public InvalidSkuException(Sku sku) {
        super("Invalid sku " + sku.value());
    }
}
