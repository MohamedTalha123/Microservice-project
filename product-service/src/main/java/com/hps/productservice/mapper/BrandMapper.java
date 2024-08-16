package com.hps.productservice.mapper;

import com.hps.productservice.dto.BrandRequest;
import com.hps.productservice.dto.BrandResponse;
import com.hps.productservice.entity.Brand;
import org.springframework.stereotype.Service;

@Service
public class BrandMapper {

    public Brand toBrand(BrandRequest request) {
        return Brand.builder()
                .id(request.getId())
                .name(request.getName())
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .build();
    }

    public BrandResponse toBrandResponse(Brand brand) {
        return BrandResponse.builder()
                .id(brand.getId())
                .name(brand.getName())
                .description(brand.getDescription())
                .imageUrl(brand.getImageUrl())
                .build();
    }
}
