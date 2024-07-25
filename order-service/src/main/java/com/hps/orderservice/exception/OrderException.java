package com.hps.orderservice.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class OrderException extends RuntimeException {
    private final String msg;
}
