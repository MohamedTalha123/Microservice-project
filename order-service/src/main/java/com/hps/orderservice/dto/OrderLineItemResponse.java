package com.hps.orderservice.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderLineItemResponse {
    private ProductResponse product;
    private double quantity;

}
