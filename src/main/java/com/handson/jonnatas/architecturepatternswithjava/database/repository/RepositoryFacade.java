package com.handson.jonnatas.architecturepatternswithjava.database.repository;

import com.handson.jonnatas.architecturepatternswithjava.Batch;
import com.handson.jonnatas.architecturepatternswithjava.Reference;

import java.util.List;
import java.util.Optional;

public interface RepositoryFacade {

    Batch save(Batch batch);

    List<Batch> findAll();

    Optional<Batch> findByRef(Reference ref);
}
