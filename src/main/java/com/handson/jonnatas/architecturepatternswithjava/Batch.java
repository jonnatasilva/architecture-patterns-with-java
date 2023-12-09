package com.handson.jonnatas.architecturepatternswithjava;

import lombok.*;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@EqualsAndHashCode(of = "ref")
@ToString
public class Batch implements Comparable<Batch> {

    @Getter
    private final Reference ref;
    private final Sku sku;
    private final Quantity purchasedQuantity;
    @Getter
    private final LocalDate eta;

    private final Set<OrderLine> allocations = new HashSet<>();

    public void allocate(OrderLine line) {
        if (canAllocate(line))
            this.allocations.add(line);
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

    @Override
    public int compareTo(@NonNull Batch o) {
        return Comparator.comparing(Batch::getEta, Comparator.nullsFirst(Comparator.naturalOrder()))
                .compare(this, o);
    }
}