package com.handson.jonnatas.architecturepatternswithjava;

import com.handson.jonnatas.architecturepatternswithjava.adapters.repository.RepositoryFacade;
import com.handson.jonnatas.architecturepatternswithjava.domain.Batch;
import com.handson.jonnatas.architecturepatternswithjava.domain.Reference;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FakeRepository implements RepositoryFacade {

    private final Set<Batch> batches;

    public FakeRepository() {
        this.batches = new HashSet<>();
    }

    public FakeRepository(Set<Batch> batches) {
        this.batches = new HashSet<>(batches);
    }

    @Override
    public Batch save(Batch batch) {
        batches.add(batch);

        return batch;
    }

    @Override
    public Set<Batch> findAll() {
        return batches;
    }

    @Override
    public Optional<Batch> findByRef(Reference ref) {
        return batches.stream()
                .filter(batch -> batch.getRef().equals(ref))
                .findFirst();
    }
}
