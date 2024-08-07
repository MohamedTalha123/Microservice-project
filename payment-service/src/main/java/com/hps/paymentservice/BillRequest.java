package com.hps.paymentservice;

import lombok.Data;

@Data
public class BillRequest {

    private String phone;

    private Long clientId;

    private Long orderId;

}
