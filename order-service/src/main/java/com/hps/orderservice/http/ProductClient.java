package com.hps.orderservice.http;

import com.hps.orderservice.dto.ProductResponse;
import com.hps.orderservice.dto.PurchaseRequest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

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

}

