package com.handson.jonnatas.architecturepatternswithjava.controller;

import com.handson.jonnatas.architecturepatternswithjava.*;
import com.handson.jonnatas.architecturepatternswithjava.adapters.repository.RepositoryFacade;
import com.handson.jonnatas.architecturepatternswithjava.domain.Batch;
import com.handson.jonnatas.architecturepatternswithjava.domain.OrderLine;
import com.handson.jonnatas.architecturepatternswithjava.domain.Quantity;
import com.handson.jonnatas.architecturepatternswithjava.domain.Sku;
import com.handson.jonnatas.architecturepatternswithjava.domain.exceptions.InvalidSkuException;
import com.handson.jonnatas.architecturepatternswithjava.domain.exceptions.OutOfStockException;
import com.handson.jonnatas.architecturepatternswithjava.service.Services;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class Api {

    private final RepositoryFacade repository;

    @PostMapping("/allocate")
    public ResponseEntity<Map<String, Object>> allocate(@RequestBody Map<String, Object> request) {
        try {
            var orderLine = new OrderLine((String) request.get("orderId"),
                    Sku.of((String) request.get("sku")),
                    Quantity.of((Integer) request.get("qty")));

            var batchRef = Services.allocate(orderLine, repository, new FakeSession());

            return new ResponseEntity<>(Map.of("batchRef", batchRef.value()), HttpStatus.CREATED);
        } catch (OutOfStockException | InvalidSkuException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    private boolean isValidSku(Sku sku, Set<Batch> batches) {
        return batches.stream()
                .anyMatch(batch -> batch.getSku().equals(sku));
    }
}
