package com.hps.paymentservice.dto;

import lombok.Data;

@Data
public class BillRequest {

    private String phone;

    private Long clientId;

    private Long orderId;

}
