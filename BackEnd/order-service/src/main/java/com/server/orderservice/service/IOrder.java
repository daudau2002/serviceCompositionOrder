package com.server.orderservice.service;

import com.server.orderservice.dto.OrderDTO;

import java.util.List;

public interface IOrder {
    public OrderDTO addOrder(OrderDTO order);
    public OrderDTO getOrderById(int id);
    public List<OrderDTO> getAllOrders();
    public void deleteOrder(int id);
    public OrderDTO updateOrder(OrderDTO order);
}
