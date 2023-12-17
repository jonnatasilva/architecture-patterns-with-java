package com.handson.jonnatas.architecturepatternswithjava.database.repository.impl;

import com.handson.jonnatas.architecturepatternswithjava.Batch;
import com.handson.jonnatas.architecturepatternswithjava.database.orm.BatchORM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BatchRepository extends JpaRepository<BatchORM, String> {

    Batch save(Batch batch);
}
