package com.hps.productservice.service;

import com.hps.productservice.dto.ProductPurchaseRequest;
import com.hps.productservice.dto.ProductPurchaseResponse;
import com.hps.productservice.dto.ProductRequest;
import com.hps.productservice.dto.ProductResponse;
import com.hps.productservice.entity.Product;
import com.hps.productservice.exception.ProductPurchaseException;
import com.hps.productservice.mapper.ProductMapper;
import com.hps.productservice.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public Long createProduct(ProductRequest request) {
        var product = productMapper.toProduct(request);
        return productRepository.save(product).getId();
    }

    public boolean checkProductAvailability(ProductPurchaseRequest request) {
        Long productId = request.product_id();
        if (productId == null) {
            throw new IllegalArgumentException("Product ID must not be null");
        }

        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            Product p = product.get();
            return p.getAvailableQuantity() >= request.quantity();
        }
        return Boolean.FALSE;
    }

    public Boolean updateProductsQuantity(List<ProductPurchaseRequest> request) {
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::product_id)
                .toList();

        var storedProducts = productRepository.findAllById(productIds);


        Map<Long, Product> productMap = storedProducts.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        List<ProductPurchaseResponse> purchasedProducts = new ArrayList<>();

        for (ProductPurchaseRequest productRequest : request) {
            var productId = productRequest.product_id();
            var product = productMap.get(productId);

            if (product != null) {
                var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
                if (newAvailableQuantity < 0) {
                    throw new IllegalArgumentException("Requested quantity exceeds available quantity for product ID: " + productId);
                }
                product.setAvailableQuantity(newAvailableQuantity);
                productRepository.save(product);
                purchasedProducts.add(productMapper.toProductPurchaseResponse(product, productRequest.quantity()));
            } else {
                throw new IllegalArgumentException("Product not found for ID: " + productId);
            }
        }
        return Boolean.TRUE;
    }


    public ProductResponse getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with the ID:: "+productId))
                ;
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public void deleteProduct(Long productId) {
         productRepository.deleteById(productId);
    }

    public Long updateProduct(Long productId, ProductRequest request) {
        var product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with the ID:: " + productId));
        product.setName(request.name());
        product.setDescription(request.description());
        product.setImageUrl(request.imageUrl());
        product.setAvailableQuantity(request.availableQuantity());
        product.setPrice(request.price());
        return productRepository.save(product).getId();
    }
}
