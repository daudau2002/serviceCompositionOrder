package com.server.orderservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private String orderTime;
    private String shippingAddress;
    private String nameReceiver;
    private String phoneReceiver;
    private String status;
    private BigDecimal priceTotal;
    List<OrderItemDTO> orderItems;
}
