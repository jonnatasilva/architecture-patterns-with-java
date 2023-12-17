package com.handson.jonnatas.architecturepatternswithjava.unit;

import com.handson.jonnatas.architecturepatternswithjava.domain.*;
import com.handson.jonnatas.architecturepatternswithjava.domain.exceptions.OutOfStockException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static com.handson.jonnatas.architecturepatternswithjava.domain.Model.allocate;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestAllocate {

    @Test
    void testPrefersCurrentStockBatchesToShipments() {
        var inStockBatch = new Batch(new Reference("in-stock-batch"), new Sku("RETRO-CLOCK"), new Quantity(100), null);

        var shipmentBatch = new Batch(new Reference("shipment-batch"), new Sku("RETRO-CLOCK"), new Quantity(100), LocalDate.now().plusDays(1));

        var line = new OrderLine("oref", new Sku("RETRO-CLOCK"), new Quantity(10));

        allocate(line, Set.of(inStockBatch, shipmentBatch));

        assert inStockBatch.getAvailableQuantity() == 90;
        assert shipmentBatch.getAvailableQuantity() == 100;
    }

    @Test
    void testPrefersEarlierBatches() {
        var earliest = new Batch(new Reference("speedy-batch"), new Sku("MINIMALIST-SPOON"), new Quantity(100), LocalDate.now());

        var medium = new Batch(new Reference("normal-batch"), new Sku("MINIMALIST-SPOON"), new Quantity(100), LocalDate.now().plusDays(1));

        var latest = new Batch(new Reference("slow-batch"), new Sku("MINIMALIST-SPOON"), new Quantity(100), LocalDate.now().plusDays(30));

        var line = new OrderLine("order1", new Sku("MINIMALIST-SPOON"), new Quantity(10));

        allocate(line, Set.of(medium, earliest, latest));

        assert earliest.getAvailableQuantity() == 90;
        assert medium.getAvailableQuantity() == 100;
        assert latest.getAvailableQuantity() == 100;
    }

    @Test
    void testReturnsAllocatedBatchRef() {
        var inStockBatch = new Batch(new Reference("in-stock-batch"), new Sku("HIGHBROW-POSTER"), new Quantity(100), null);

        var shipmentBatch = new Batch(new Reference("shipment-batch"), new Sku("HIGHBROW-POSTER"), new Quantity(100), LocalDate.now().plusDays(1));

        var line = new OrderLine("oref", new Sku("HIGHBROW-POSTER"), new Quantity(10));

        var allocation = allocate(line, Set.of(inStockBatch, shipmentBatch));

        assert allocation == inStockBatch.getRef();
    }

    @Test
    void testRaisesOutOfStockExceptionIfCanNotAllocate() {
        var batch = new Batch(new Reference("shipment-batch"), new Sku("SMALL-FORK"), new Quantity(1), LocalDate.now().plusDays(1));

        var line = new OrderLine("order1", new Sku("SMALL-FORK"), new Quantity(10));

        assertThrows(OutOfStockException.class, () -> allocate(line, Set.of(batch)), "Out of stock for value SMALL-FORK");

        allocate(new OrderLine("order2", new Sku("SMALL-FORK"), new Quantity(1)), Set.of(batch));
    }
}
