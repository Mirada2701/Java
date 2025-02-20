package org.example.dto.product;

import lombok.Data;

@Data
public class ProductItemDTO {
    private Integer id;
    private String name;
    private String description;
    private float price;
    private Integer amount;
    private String categoryName;
    private String dateCreated;
}
