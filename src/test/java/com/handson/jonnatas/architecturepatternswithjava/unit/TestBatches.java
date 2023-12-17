package com.handson.jonnatas.architecturepatternswithjava.unit;

import com.handson.jonnatas.architecturepatternswithjava.domain.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class TestBatches {

    @Test
    void testAllocationToABatchReducesTheAvailableQuantity () {
        var batch = makeBatch("SMALL-TABLE", 20);

        var line = makeOrderLine("SMALL-TABLE", 2);

        batch.allocate(line);

        assert batch.getAvailableQuantity() == 18;
    }

    @Test
    void testCanAllocateIfAvailableGreaterThanRequired() {
        var largeBatch = makeBatch("ELEGANT-LAMP", 20);

        var smallLine = makeOrderLine("ELEGANT-LAMP",2);

        largeBatch.allocate(smallLine);
    }

    @Test
    void testCannotAllocateIfAvailableSmallerThanRequired() {
        var smallBatch = makeBatch("ELEGANT-LAMP", 2);

        var largeLine = makeOrderLine("ELEGANT-LAMP",20);

        assert !smallBatch.canAllocate(largeLine);
    }

    @Test
    void testCanAllocateIfAvailableEqualToRequired() {
        var batch = makeBatch("ELEGANT-LAMP", 2);

        var line = makeOrderLine("ELEGANT-LAMP",2);

        assert batch.canAllocate(line);
    }

    @Test
    void testCannotAllocateIfSkusDoNotMatch() {
        var batch = makeBatch("UNCOMFORTABLE-CHAIR", 2);

        var differentSkuLine = makeOrderLine("EXPENSIVE",2);

        assert !batch.canAllocate(differentSkuLine);
    }

    @Test
    void testCanDeallocateAllocatedLines() {
        var batch = makeBatch("DECORATIVE-TRINKET", 20);

        var unallocatedLine = makeOrderLine("DECORATIVE-TRINKET",2);

        batch.deallocate(unallocatedLine);

        assert batch.getAvailableQuantity() == 20;
    }

    @Test
    void testAllocationIsIdempotent() {
        var batch = makeBatch("DECORATIVE-TRINKET", 20);

        var line = makeOrderLine("DECORATIVE-TRINKET",2);

        batch.allocate(line);

        assert batch.getAvailableQuantity() == 18;
    }

    Batch makeBatch(String sku, int batchQty) {
        return new Batch(new Reference("batch-001"), new Sku(sku), new Quantity(batchQty), LocalDate.now());
    }

    OrderLine makeOrderLine(String sku, int lineQty) {
        return new OrderLine("order-123", new Sku(sku), new Quantity(lineQty));
    }
}