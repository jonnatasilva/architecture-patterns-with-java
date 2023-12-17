package com.handson.jonnatas.architecturepatternswithjava.adapters.repository.impl;

import com.handson.jonnatas.architecturepatternswithjava.domain.Batch;
import com.handson.jonnatas.architecturepatternswithjava.adapters.orm.BatchORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BatchRepository extends JpaRepository<BatchORM, String> {

    Batch save(Batch batch);
}
