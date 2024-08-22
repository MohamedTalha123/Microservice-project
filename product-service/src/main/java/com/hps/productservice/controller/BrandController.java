package com.hps.productservice.controller;

import com.hps.productservice.dto.BrandRequest;
import com.hps.productservice.dto.BrandResponse;
import com.hps.productservice.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<Long> createBrand(@RequestBody BrandRequest request) {
        return ResponseEntity.ok(brandService.createBrand(request));
    }

    @GetMapping("/public/{brandId}")
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable("brandId") Long brandId) {
        return ResponseEntity.ok(brandService.getBrandById(brandId));
    }

    @GetMapping("/public")
    public ResponseEntity<List<BrandResponse>> findAll() {
        return ResponseEntity.ok(brandService.findAll());
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<Void> delete(@PathVariable("brandId") Long brandId) {
        brandService.deleteBrand(brandId);
        return ResponseEntity.accepted().build();
    }



    @PutMapping("/{brandId}")
    public ResponseEntity<Long> updateBrand(@PathVariable("brandId") Long brandId, @RequestBody BrandRequest request) {
        return ResponseEntity.ok(brandService.updateBrand(brandId, request));
    }
}
