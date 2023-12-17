package com.handson.jonnatas.architecturepatternswithjava.database.mapper;

import com.handson.jonnatas.architecturepatternswithjava.OrderLine;
import com.handson.jonnatas.architecturepatternswithjava.database.orm.OrderLineORM;
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
