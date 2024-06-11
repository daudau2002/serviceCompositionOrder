package com.server.orderservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderItemDTO {
    private Long id;
    private Long itemId;
    private Long quantity;
    private Long orderId;

    List<OrderItemDTO> listItems;
}
