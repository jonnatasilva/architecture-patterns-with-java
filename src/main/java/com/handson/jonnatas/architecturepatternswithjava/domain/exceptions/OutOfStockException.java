package com.handson.jonnatas.architecturepatternswithjava.domain.exceptions;

import com.handson.jonnatas.architecturepatternswithjava.domain.Sku;
import lombok.Getter;

@Getter
public class OutOfStockException extends RuntimeException {

    public OutOfStockException(Sku sku) {
        super("Out of stock for sku " + sku.value());
    }
}
