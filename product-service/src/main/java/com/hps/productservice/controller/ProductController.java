package com.hps.productservice.controller;

import com.hps.productservice.dto.*;
import com.hps.productservice.entity.Sexe;
import com.hps.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Long> createProduct(@RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PostMapping("/check")
    public ResponseEntity<Boolean> checkProductAvailability(@RequestBody ProductPurchaseRequest request){
        boolean isAvailable = productService.checkProductAvailability(request);
        return ResponseEntity.ok(isAvailable);
    }
    @PostMapping("/update")
    public ResponseEntity<Boolean> updateProductsQuantity(@RequestBody List<ProductPurchaseRequest> request) {
        return ResponseEntity.ok(productService.updateProductsQuantity(request));
    }
    
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable ("productId") Long productId){
        return ResponseEntity.ok( productService.getProductById(productId));
    }
//    @GetMapping("/sexe/{sexe}")
//    public ResponseEntity<List<ProductResponse>> findProductsBySexe(@PathVariable String sexe) {
//        Sexe sexeEnum = Sexe.valueOf(sexe.toUpperCase());
//        List<ProductResponse> products = productService.findProductsBySexe(sexeEnum);
//        return ResponseEntity.ok(products);
//    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.accepted().build();
    }
    @PutMapping("/{productId}")
    public ResponseEntity<Long> updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(productId, request));
    }





}
