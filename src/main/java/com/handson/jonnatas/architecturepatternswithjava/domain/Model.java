package com.handson.jonnatas.architecturepatternswithjava.domain;

import com.handson.jonnatas.architecturepatternswithjava.domain.exceptions.OutOfStockException;

import java.util.Set;

public class Model {

    private Model() {}

    public static Reference allocate(OrderLine line, Set<Batch> batches) {
        var batch = batches.stream()
                .filter(b -> b.canAllocate(line))
                .sorted()
                .findFirst()
                .orElseThrow(() -> new OutOfStockException(line.sku()));

        batch.allocate(line);

        return batch.getRef();
    }
}
