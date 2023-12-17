package com.handson.jonnatas.architecturepatternswithjava.mapper;

import com.handson.jonnatas.architecturepatternswithjava.domain.Quantity;
import org.mapstruct.Mapper;

@Mapper
public interface QuantityMapper {

    default int map(Quantity quantity) {
        return quantity.value();
    }

    Quantity map(String value);
}
