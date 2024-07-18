package com.hps.productservice.mapper;

import com.hps.productservice.dto.ProductPurchaseResponse;
import com.hps.productservice.dto.ProductRequest;
import com.hps.productservice.dto.ProductResponse;
import com.hps.productservice.entity.Category;
import com.hps.productservice.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class  ProductMapper {
    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .availableQuantity(request.availableQuantity())
                .imageUrl(request.imageUrl())
                .category(Category.builder()
                        .id(request.categoryId())
                        .build()
                )
                .build();
    }

    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getAvailableQuantity(),
                        product.getPrice(),
                        product.getImageUrl(),
                        product.getCategory().getId(),
                        product.getCategory().getName(),
                        product.getCategory().getDescription()

                );
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
