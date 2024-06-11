package com.server.orderservice.converter;


import com.server.orderservice.dto.OrderItemDTO;
import com.server.orderservice.modal.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {
    public OrderItemDTO entityToDto(OrderItem orderItem) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setId(orderItem.getId());
        orderItemDTO.setItemId(orderItem.getItemId());
        orderItemDTO.setQuantity(orderItem.getQuantity());
        return orderItemDTO;
    }

    public OrderItem dtoToEntity(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemId(orderItemDTO.getItemId());
        orderItem.setQuantity(orderItemDTO.getQuantity());
        return orderItem;
    }

}
