package com.handson.jonnatas.architecturepatternswithjava.database.orm;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "order_lines")
@Access(AccessType.FIELD)
public class OrderLineORM {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sku")
    private String sku;

    @Column(name = "qty")
    private int qty;

    @Column(name = "order_id")
    private String orderId;
}