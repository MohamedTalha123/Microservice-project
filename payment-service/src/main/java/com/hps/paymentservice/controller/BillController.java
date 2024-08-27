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

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;
    @GetMapping
    public ResponseEntity<List<Bill>> getAll(){
        return ResponseEntity.ok(billService.getAll());
    }

    @PostMapping("/create-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo) throws StripeException {
        PaymentIntent paymentIntent = billService.createPaymentIntent(paymentInfo);
        String paymentStr = paymentIntent.toJson();
        return ResponseEntity.ok(paymentStr);
    }
    @PostMapping("/create-bill")
    public ResponseEntity<Bill> createBill(@RequestBody BillRequest billRequest) {
        Bill bill = billService.createBill(billRequest);
        return ResponseEntity.ok(bill);
    }

    @PostMapping("/pay-bill")
    public ResponseEntity<String> payBill(@RequestBody String phone) {
        String response = billService.payBill(phone);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteBill() {
        billService.deleteBill();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/confirm-payment")
    public ResponseEntity<String> confirmPayment(@RequestBody String verificationCode) {
        String response = billService.confirmBillPayment(verificationCode);
        return ResponseEntity.ok(response);
    }


}
