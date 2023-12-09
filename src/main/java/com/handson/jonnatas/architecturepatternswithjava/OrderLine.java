package com.handson.jonnatas.architecturepatternswithjava;

public record  OrderLine (String orderId, Sku sku, Quantity qty) {}