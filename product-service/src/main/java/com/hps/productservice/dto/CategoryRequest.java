package com.hps.productservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequest {
    private Long id;
    private String name;
    private String description;
}
