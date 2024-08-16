package com.hps.productservice.repository;

import com.hps.productservice.dto.ProductResponse;
import com.hps.productservice.entity.Product;
import com.hps.productservice.entity.Sexe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

//    List<ProductResponse> findAllBySexe(Sexe sexe);
    Optional<ProductResponse> findByReference (String reference);

    List<Product> findByBrand_Name(String brandName);
}
