package com.hps.paymentservice.controller;

import com.hps.paymentservice.dto.BillRequest;
import com.hps.paymentservice.dto.PaymentInfo;
import com.hps.paymentservice.entity.Bill;
import com.hps.paymentservice.service.BillService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

//    @PostMapping("/create-intent")
//    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
//        PaymentIntent paymentIntent = billService.createPaymentIntent(paymentInfo);
//        String paymentStr = paymentIntent.toJson();
//        return ResponseEntity.ok(paymentStr);
//    }
    @PostMapping("/create-bill")
    public ResponseEntity<Bill> createBill(@RequestBody BillRequest billRequest) {
        Bill bill = billService.createBill(billRequest);
        return ResponseEntity.ok(bill);
    }

    @PostMapping("/pay-bill")
    public ResponseEntity<String> payBill(@RequestBody BillRequest billRequest) {
        String response = billService.payBill(billRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm-payment")
    public ResponseEntity<String> confirmPayment(@RequestParam String verificationCode) {
        String response = billService.confirmBillPayment(verificationCode);
        return ResponseEntity.ok(response);
    }


}
