package com.hps.productservice.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //todo: add reference
    //todo: change id type to UUID
    private String name;
    private String description;
    private double availableQuantity;
    private BigDecimal price;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private Sexe sexe;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category ;

}
