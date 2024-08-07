package com.hps.productservice.service;

import com.hps.productservice.dto.BrandRequest;
import com.hps.productservice.dto.BrandResponse;
import com.hps.productservice.mapper.BrandMapper;
import com.hps.productservice.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public Long createBrand(BrandRequest request) {
        var brand = brandMapper.toBrand(request);
        return brandRepository.save(brand).getId();
    }

    public BrandResponse getBrandById(Long brandId) {
        return brandRepository.findById(brandId)
                .map(brandMapper::toBrandResponse)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with the ID:: " + brandId));
    }

    public List<BrandResponse> findAll() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toBrandResponse)
                .collect(Collectors.toList());
    }

    public void deleteBrand(Long brandId) {
        brandRepository.deleteById(brandId);
    }

    public Long updateBrand(Long brandId, BrandRequest request) {
        var brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found with the ID:: " + brandId));
        brand.setName(request.getName());
        brand.setDescription(request.getDescription());
        return brandRepository.save(brand).getId();
    }
}
