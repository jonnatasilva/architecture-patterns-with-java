package com.handson.jonnatas.architecturepatternswithjava.database.repository.impl;

import com.handson.jonnatas.architecturepatternswithjava.Batch;
import com.handson.jonnatas.architecturepatternswithjava.Reference;
import com.handson.jonnatas.architecturepatternswithjava.database.mapper.BatchMapper;
import com.handson.jonnatas.architecturepatternswithjava.database.repository.RepositoryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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
    public List<Batch> findAll() {
        return batchRepository.findAll().stream()
                .map(BatchMapper.INSTANCE::map)
                .toList();
    }

    @Override
    public Optional<Batch> findByRef(Reference ref) {
        return batchRepository.findById(ref.value())
                .map(BatchMapper.INSTANCE::map);
    }
}
