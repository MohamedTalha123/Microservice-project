package com.hps.productservice.service;

import com.hps.productservice.dto.ProductPurchaseRequest;
import com.hps.productservice.dto.ProductPurchaseResponse;
import com.hps.productservice.dto.ProductRequest;
import com.hps.productservice.dto.ProductResponse;
import com.hps.productservice.exception.ProductPurchaseException;
import com.hps.productservice.mapper.ProductMapper;
import com.hps.productservice.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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

    public List<ProductPurchaseResponse> purchaseProduct(List<ProductPurchaseRequest>request) {
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProdects = productRepository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProdects.size()){
            throw new ProductPurchaseException("One or more product does not exist");
        }
        var storedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProduct = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProdects.size(); i++) {
            var product = storedProdects.get(i);
            var productRequest = storedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID::"+productRequest.productId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            productRepository.save(product);
            purchasedProduct.add(productMapper.toProductPurchaseResponse(product, productRequest.quantity()));
            
        }
        return purchasedProduct;
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
}
