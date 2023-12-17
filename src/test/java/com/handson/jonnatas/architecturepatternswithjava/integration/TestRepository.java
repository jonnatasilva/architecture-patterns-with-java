package com.handson.jonnatas.architecturepatternswithjava.integration;

import com.handson.jonnatas.architecturepatternswithjava.*;
import com.handson.jonnatas.architecturepatternswithjava.adapters.orm.BatchORM;
import com.handson.jonnatas.architecturepatternswithjava.adapters.orm.OrderLineORM;
import com.handson.jonnatas.architecturepatternswithjava.adapters.repository.RepositoryFacade;
import com.handson.jonnatas.architecturepatternswithjava.domain.*;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = ArchitecturePatternsWithJavaApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class TestRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RepositoryFacade batchRepository;

    @Test
    void testRepositoryCanSaveABatch() {
        var line1 = new OrderLineORM(null,"RED-CHAIR", 12, "order1");
        var line2 = new OrderLineORM(null,"RED-TABLE", 13, "order1");
        var line3 = new OrderLineORM(null,"BLUE-LIPSTICK", 14, "order2");

        var em = entityManager.getEntityManagerFactory().createEntityManager();
        var session = em.unwrap(Session.class);
        session.beginTransaction();

        em.persist(line1);
        em.persist(line2);
        em.persist(line3);

        session.getTransaction().commit();

        List<OrderLineORM> lines = entityManager.createQuery("SELECT ol FROM OrderLineORM ol", OrderLineORM.class)
                .getResultList()
                .stream()
                .toList();


        assert lines.equals(List.of(line1, line2, line3));
    }

    @Test
    void testRepositoryCanRetrieveABatchWithAllocations() {
        var em = entityManager.getEntityManagerFactory().createEntityManager();
        var session = em.unwrap(Session.class);
        session.beginTransaction();

        var orderLineId = insertOrderLine(em);

        var batchId = insertBatch(em);

        insertAllocation(em, orderLineId, batchId);

        session.getTransaction().commit();

        Optional<Batch> byRef = batchRepository.findByRef(Reference.of(batchId));


        assert byRef.isPresent();

        var batch = byRef.get();

        assert batch.getRef().equals(Reference.of("batch1"));
        assert batch.getSku().equals(Sku.of("GENERIC-SOFA"));
        assert batch.getEta() == null;
        assert batch.getPurchasedQuantity().equals(Quantity.of(10));
        assert batch.getAllocations().equals(Set.of(new OrderLine("order1", Sku.of("GENERIC-SOFA"), Quantity.of(1))));
    }

    Long insertOrderLine(EntityManager em) {
        var ol = new OrderLineORM();
        ol.setOrderId("order1");
        ol.setSku("GENERIC-SOFA");
        ol.setQty(1);

        em.persist(ol);

        return (Long) em.createQuery("SELECT id FROM OrderLineORM WHERE orderId = :orderId AND sku = :sku")
                .setParameter("orderId", "order1")
                .setParameter("sku", "GENERIC-SOFA")
                .getSingleResult();
    }

    String insertBatch(EntityManager em) {
        var batch = new BatchORM();
        batch.setRef("batch1");
        batch.setSku("GENERIC-SOFA");
        batch.setPurchasedQuantity(10);

        em.persist(batch);

        return (String) em.createQuery("SELECT ref FROM BatchORM WHERE ref = :ref AND sku = :sku")
                .setParameter("ref", "batch1")
                .setParameter("sku", "GENERIC-SOFA")
                .getSingleResult();
    }

    void insertAllocation(EntityManager em, Long orderLineId, String batchId) {
        var batch = em.createQuery("SELECT b FROM BatchORM b WHERE b.ref = :ref", BatchORM.class)
                .setParameter("ref", batchId)
                .getSingleResult();

        var orderLine  = em.createQuery("SELECT b FROM OrderLineORM b WHERE b.id = :orderLineId", OrderLineORM.class)
                .setParameter("orderLineId", orderLineId)
                .getSingleResult();

        batch.setAllocations(Set.of(orderLine));

        em.persist(orderLine);
    }
}
