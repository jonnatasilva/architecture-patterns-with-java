package com.handson.jonnatas.architecturepatternswithjava.adapters.repository;

import com.handson.jonnatas.architecturepatternswithjava.domain.Batch;
import com.handson.jonnatas.architecturepatternswithjava.domain.Reference;

import java.util.Optional;
import java.util.Set;

public interface RepositoryFacade {

    Batch save(Batch batch);

    Set<Batch> findAll();

    Optional<Batch> findByRef(Reference ref);
}
