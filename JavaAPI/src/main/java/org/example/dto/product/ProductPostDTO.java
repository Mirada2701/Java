package org.example.dto.product;

import lombok.Data;
import org.example.entities.CategoryEntity;

@Data
public class ProductPostDTO {
    private String name;
    private String description;
    private float price;
    private Integer amount;
    private CategoryEntity category;
}