package com.handson.jonnatas.architecturepatternswithjava.adapters.mapper;

import com.handson.jonnatas.architecturepatternswithjava.domain.Batch;
import com.handson.jonnatas.architecturepatternswithjava.adapters.orm.BatchORM;
import com.handson.jonnatas.architecturepatternswithjava.mapper.QuantityMapper;
import com.handson.jonnatas.architecturepatternswithjava.mapper.ReferenceMapper;
import com.handson.jonnatas.architecturepatternswithjava.mapper.SkuMapper;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { OrderLineMapper.class, ReferenceMapper.class, SkuMapper.class, QuantityMapper.class },
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface BatchMapper {
    BatchMapper INSTANCE = Mappers.getMapper( BatchMapper.class );
    Batch map(BatchORM batch);

    BatchORM map(Batch batch);
}
