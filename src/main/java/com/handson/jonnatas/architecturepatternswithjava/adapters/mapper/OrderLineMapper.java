package com.handson.jonnatas.architecturepatternswithjava.adapters.mapper;

import com.handson.jonnatas.architecturepatternswithjava.domain.OrderLine;
import com.handson.jonnatas.architecturepatternswithjava.adapters.orm.OrderLineORM;
import com.handson.jonnatas.architecturepatternswithjava.mapper.QuantityMapper;
import com.handson.jonnatas.architecturepatternswithjava.mapper.SkuMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { SkuMapper.class, QuantityMapper.class })
public interface OrderLineMapper {

    OrderLineMapper INSTANCE = Mappers.getMapper(OrderLineMapper.class);

    OrderLine map(OrderLineORM line);

    OrderLineORM map(OrderLine line);
}
