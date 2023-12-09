package com.handson.jonnatas.architecturepatternswithjava.exceptions;

import com.handson.jonnatas.architecturepatternswithjava.Sku;
import lombok.Getter;

@Getter
public class OutOfStockException extends RuntimeException {

    public OutOfStockException(Sku sku) {
        super("Out of stock for sku" + sku.value());
    }
}
