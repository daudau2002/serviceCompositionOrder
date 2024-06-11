package com.server.itemservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDTO {
    private Long id;
    private String name;
    private Long quantity;
    private BigDecimal price;
    private String description;
    private String image;
    private String category;
}
