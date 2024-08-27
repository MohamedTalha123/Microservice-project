package com.hps.orderservice.http;

import com.hps.orderservice.dto.BillRequest;
import com.hps.orderservice.dto.BillResponse;
import com.hps.orderservice.dto.PaymentInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "payment-service",
        url = "${application.config.payment-url}"
)
public interface PaymentClient {

    @PostMapping("/pay-bill")
    String payBill(@RequestBody String phone);
    @PostMapping("/confirm-payment")
    String confirmBillPayment(@RequestBody String verificationCode);
    @PostMapping("/create-bill")
    BillResponse createBill(@RequestBody BillRequest billRequest);
    @PostMapping("/create-intent")
    String createPaymentIntent(@RequestBody PaymentInfo paymentInfo);
    @DeleteMapping
    void deleteBill();
}

