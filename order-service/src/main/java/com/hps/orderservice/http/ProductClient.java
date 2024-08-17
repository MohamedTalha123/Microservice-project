package com.hps.orderservice.http;

import com.hps.orderservice.dto.ProductResponse;
import com.hps.orderservice.dto.PurchaseRequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@FeignClient(
        name = "product-service",
        url = "${application.config.product-url}"
)
public interface ProductClient {
    @PostMapping("/check")
    Boolean checkProductAvailability(@RequestBody PurchaseRequest purchaseRequest);
    @PostMapping("/update")
    Boolean updateProductsQuantity(@RequestBody List<PurchaseRequest> purchaseRequest);
    @GetMapping("/{productId}")
    Optional<ProductResponse> getProductById(@PathVariable("productId") Long productId);

    @GetMapping
    ResponseEntity<List<ProductResponse>> findAllById(@RequestParam ("id")Set<Long> id);

}

