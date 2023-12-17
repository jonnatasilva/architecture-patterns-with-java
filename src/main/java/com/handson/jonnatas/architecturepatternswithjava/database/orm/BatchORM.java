package com.handson.jonnatas.architecturepatternswithjava.database.orm;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "batches")
@Access(AccessType.FIELD)
public class BatchORM {

    @Id
    @Column(name = "ref")
    private String ref;

    @Column(name = "sku")
    private String sku;

    @Column(name = "purchased_quantity")
    private int purchasedQuantity;

    @Column(name = "eta")
    private LocalDate eta;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "batches_order_lines",
            joinColumns = @JoinColumn(name = "batch_id", referencedColumnName = "ref"),
            inverseJoinColumns = @JoinColumn(name = "order_line_id", referencedColumnName = "id")
    )
    private Set<OrderLineORM> allocations = new HashSet<>();
}