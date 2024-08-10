package com.hps.orderservice.http;

import com.hps.orderservice.dto.BillRequest;
import com.hps.orderservice.dto.BillResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "payment-service",
        url = "${application.config.payment-url}"
)
public interface PaymentClient {
    @PostMapping("/create-bill")
    BillResponse createBill(@RequestBody BillRequest billRequests);
}
