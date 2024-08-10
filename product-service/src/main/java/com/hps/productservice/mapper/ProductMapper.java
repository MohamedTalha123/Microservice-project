package com.hps.productservice.mapper;

import com.hps.productservice.dto.ProductPurchaseResponse;
import com.hps.productservice.dto.ProductRequest;
import com.hps.productservice.dto.ProductResponse;
import com.hps.productservice.entity.Brand;
import com.hps.productservice.entity.Product;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductMapper {

    public Product toProduct(ProductRequest request) {
        return Product.builder()


                .description(request.description())
                .reference(generateReference())
                .name(request.name())
                .price(request.price())
                .availableQuantity(request.availableQuantity())
                .imageUrl(request.imageUrl())
                .sexe(request.sexe())
                .brand(Brand.builder()
                        .id(request.brandId())
                        .build()
                )
                .build();
    }


    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),

                product.getDescription(),
                product.getReference(),
                product.getName(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getImageUrl(),
                product.getSexe(),
                product.getBrand().getId(),
                product.getBrand().getName(),
                product.getBrand().getDescription()
        );
    }
    private String generateReference() {
        String ref = UUID.randomUUID().toString().substring(0, 8);
        return "PP-".concat(ref);
    }

    public ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity,
                product.getImageUrl()
        );
    }
}
