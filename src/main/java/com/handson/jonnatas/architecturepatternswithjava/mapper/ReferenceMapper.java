package com.handson.jonnatas.architecturepatternswithjava.mapper;

import com.handson.jonnatas.architecturepatternswithjava.domain.Reference;
import org.mapstruct.Mapper;

@Mapper
public interface ReferenceMapper {

    default String map(Reference reference) {
        if (reference == null)
            return null;

        return reference.value();
    }

    Reference map(String value);
}
