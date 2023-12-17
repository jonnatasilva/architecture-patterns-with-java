package com.handson.jonnatas.architecturepatternswithjava.adapters.repository.impl;

import com.handson.jonnatas.architecturepatternswithjava.domain.Batch;
import com.handson.jonnatas.architecturepatternswithjava.domain.Reference;
import com.handson.jonnatas.architecturepatternswithjava.adapters.mapper.BatchMapper;
import com.handson.jonnatas.architecturepatternswithjava.adapters.repository.RepositoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class RepositoryFacadeImpl implements RepositoryFacade {

    private final BatchRepository batchRepository;

    @Override
    public Batch save(Batch batch) {
        var orm = BatchMapper.INSTANCE.map(batch);

        var saved = batchRepository.save(orm);

        return BatchMapper.INSTANCE.map(saved);

    }

    @Override
    public Set<Batch> findAll() {
        return batchRepository.findAll().stream()
                .map(BatchMapper.INSTANCE::map)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Batch> findByRef(Reference ref) {
        return batchRepository.findById(ref.value())
                .map(BatchMapper.INSTANCE::map);
    }
}
