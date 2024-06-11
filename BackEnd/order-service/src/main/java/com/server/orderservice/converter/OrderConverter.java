package com.server.orderservice.converter;

import com.server.orderservice.dto.OrderDTO;
import com.server.orderservice.dto.OrderItemDTO;
import com.server.orderservice.modal.Order;
import com.server.orderservice.modal.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderConverter {
    private static final String LOCAL_TIMEZONE = "Asia/Ho_Chi_Minh";

    @Autowired
    private OrderItemConverter orderItemConverter;

    public OrderDTO entityToDto(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUserId());
        orderDTO.setOrderTime(String.valueOf(order.getOrderTime()));
        orderDTO.setShippingAddress(order.getShippingAddress());
        orderDTO.setNameReceiver(order.getNameReceiver());
        orderDTO.setPhoneReceiver(order.getPhoneReceiver());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setPriceTotal(order.getPriceTotal());

        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItemDTOS.add(orderItemConverter.entityToDto(orderItem));
        }
        orderDTO.setOrderItems(orderItemDTOS);

        return orderDTO;
    }

    public  Order dtoToEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setUserId(orderDTO.getUserId());
        order.setOrderTime(getCurrentTimeInLocalZone());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setNameReceiver(orderDTO.getNameReceiver());
        order.setPhoneReceiver(orderDTO.getPhoneReceiver());
        order.setStatus(orderDTO.getStatus());
        order.setPriceTotal(orderDTO.getPriceTotal());
        List<OrderItem> orderItems = new ArrayList<>();
        List<OrderItemDTO> orderItemDTOS = orderDTO.getOrderItems();
        for (OrderItemDTO orderItemDTO : orderItemDTOS) {
            OrderItem orderItem = orderItemConverter.dtoToEntity(orderItemDTO);
            orderItem.setOrder(order);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        return order;
    }

    public Order dtoToEntity(OrderDTO orderDTO, Order order) {
        order.setOrderTime(getCurrentTimeInLocalZone());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setStatus(orderDTO.getStatus());
        order.setPriceTotal(orderDTO.getPriceTotal());
        return order;
    }
    private LocalDateTime getCurrentTimeInLocalZone() {
        ZoneId localZone = ZoneId.of(LOCAL_TIMEZONE);
        ZonedDateTime localTime = ZonedDateTime.now(localZone);
        return localTime.toLocalDateTime();
    }
}
