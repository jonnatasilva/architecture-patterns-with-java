package com.handson.jonnatas.architecturepatternswithjava.service;

import com.handson.jonnatas.architecturepatternswithjava.*;
import com.handson.jonnatas.architecturepatternswithjava.adapters.repository.RepositoryFacade;
import com.handson.jonnatas.architecturepatternswithjava.domain.*;
import com.handson.jonnatas.architecturepatternswithjava.domain.exceptions.InvalidSkuException;

import java.util.Set;

public class Services {

    public static Reference allocate(OrderLine line, RepositoryFacade repository, FakeSession session) {
        var batches = repository.findAll();

        if (!isValidSku(line.sku(), batches)) {
            throw new InvalidSkuException(line.sku());
        }

        var batchRef = Model.allocate(line, batches);
        repository.save(batches.stream().filter(batch -> batch.getRef().equals(batchRef)).findFirst().orElseThrow());
        session.commit();

        return batchRef;
    }

    private static boolean isValidSku(Sku sku, Set<Batch> batches) {
        return batches.stream()
                .anyMatch(batch -> batch.getSku().equals(sku));
    }
}
