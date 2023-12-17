package com.handson.jonnatas.architecturepatternswithjava;

import com.handson.jonnatas.architecturepatternswithjava.database.repository.RepositoryFacade;

import java.util.*;

public class FakeRepository implements RepositoryFacade {

    private List<Batch> batches = new ArrayList<>();

    @Override
    public Batch save(Batch batch) {
        batches.add(batch);

        return batch;
    }

    @Override
    public List<Batch> findAll() {
        return batches;
    }

    @Override
    public Optional<Batch> findByRef(Reference ref) {
        return batches.stream()
                .filter(batch -> batch.getRef().equals(ref))
                .findFirst();
    }
}
