package com.handson.jonnatas.architecturepatternswithjava.mapper;

import com.handson.jonnatas.architecturepatternswithjava.Sku;
import org.mapstruct.Mapper;

@Mapper
public interface SkuMapper {

    default String map(Sku sku) {
        if (sku == null)
            return null;

        return sku.value();
    }

    Sku map(String value);
}
