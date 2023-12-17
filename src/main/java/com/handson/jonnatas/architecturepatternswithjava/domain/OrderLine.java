package com.handson.jonnatas.architecturepatternswithjava.domain;

public record OrderLine(String orderId, Sku sku, Quantity qty) {}