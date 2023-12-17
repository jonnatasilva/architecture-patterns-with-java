package com.handson.jonnatas.architecturepatternswithjava.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode(of = "ref")
@ToString
public class Batch implements Comparable<Batch> {

    private final Reference ref;
    private final Sku sku;
    private final Quantity purchasedQuantity;
    private final LocalDate eta;
    private final Set<OrderLine> allocations;

    public Batch(Reference ref, Sku sku, Quantity purchasedQuantity, LocalDate eta) {
        this.ref = ref;
        this.sku = sku;
        this.purchasedQuantity = purchasedQuantity;
        this.eta = eta;
        this.allocations = new HashSet<>();
    }


    public void allocate(OrderLine line) {
        if (canAllocate(line))
            this.allocations.add(line);
    }

    public void addAllocation(OrderLine line) {
        allocate(line);
    }

    public boolean canAllocate(OrderLine line) {
        return sku.equals(line.sku()) && getAvailableQuantity() >= line.qty().value();
    }

    public void deallocate(OrderLine line) {
        allocations.remove(line);
    }

    public int getAvailableQuantity() {
        return purchasedQuantity.value() - allocatedQuantity();
    }

    private int allocatedQuantity() {
        return allocations.stream()
                .map(OrderLine::qty)
                .map(Quantity::value)
                .reduce(0, Integer::sum);
    }

    public Set<OrderLine> getAllocations() {
        return Collections.unmodifiableSet(this.allocations);
    }

    @Override
    public int compareTo(@NonNull Batch o) {
        return Comparator.comparing(Batch::getEta, Comparator.nullsFirst(Comparator.naturalOrder()))
                .compare(this, o);
    }
}