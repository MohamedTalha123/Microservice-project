package com.hps.productservice.controller;

import com.hps.productservice.dto.ProductPurchaseRequest;
import com.hps.productservice.dto.ProductPurchaseResponse;
import com.hps.productservice.dto.ProductRequest;
import com.hps.productservice.dto.ProductResponse;
import com.hps.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class productController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProduct(@RequestBody List<ProductPurchaseRequest> request){
        return ResponseEntity.ok(productService.purchaseProduct(request));
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable ("productId") Long productId){
        return ResponseEntity.ok( productService.getProductById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.accepted().build();
    }






}
