package com.handson.jonnatas.architecturepatternswithjava.unit;

import com.handson.jonnatas.architecturepatternswithjava.*;
import com.handson.jonnatas.architecturepatternswithjava.domain.*;
import com.handson.jonnatas.architecturepatternswithjava.domain.exceptions.InvalidSkuException;
import com.handson.jonnatas.architecturepatternswithjava.service.Services;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TestService {

    @Test
    void testReturnsAllocations() {
        var line = new OrderLine("o1", Sku.of("COMPLICATED-LAMP"), Quantity.TEN);
        var batch = new Batch(Reference.of("b1"), Sku.of("COMPLICATED-LAMP"), Quantity.A_HUNDRED, null);

        var repo = new FakeRepository(Set.of(batch));

        var result = Services.allocate(line, repo, new FakeSession());

        assert result.equals(Reference.of("b1"));
    }

    @Test
    void testErrorForInvalidSku() {
        var line = new OrderLine("o1", Sku.of("NONEXISTENTSKU"), Quantity.TEN);
        var batch = new Batch(Reference.of("b1"), Sku.of("AREALSKU"), Quantity.A_HUNDRED, null);

        var repo = new FakeRepository(Set.of(batch));

        assertThrows(InvalidSkuException.class,
                () -> Services.allocate(line, repo, new FakeSession()), "Invalid sku NONEXISTENTSKU");
    }

    @Test
    void testCommits() {
        var line = new OrderLine("o1", Sku.of("OMINOUS-MIRROR"), Quantity.TEN);
        var batch = new Batch(Reference.of("b1"), Sku.of("OMINOUS-MIRROR"), Quantity.A_HUNDRED, null);

        var repo = new FakeRepository(Set.of(batch));
        var session = new FakeSession();

        Services.allocate(line, repo, session);

        assert session.commited;
    }
}
