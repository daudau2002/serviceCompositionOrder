package com.server.orderservice.service.impl;

import com.server.orderservice.converter.OrderConverter;
import com.server.orderservice.dto.OrderDTO;
import com.server.orderservice.modal.Order;
import com.server.orderservice.repository.OrderRepository;
import com.server.orderservice.service.IOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrder {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderConverter orderConverter;

    @Override
    public OrderDTO addOrder(OrderDTO order) {
        Order newOrder = orderConverter.dtoToEntity(order);
        try {
            Order savedOrder = orderRepository.save(newOrder);
            return orderConverter.entityToDto(savedOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrderDTO getOrderById(int id) {
        Order order = orderRepository.findById((long) id).orElse(null);
        if (order == null) {
            return null;
        }
        return orderConverter.entityToDto(order);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> orderConverter.entityToDto(order)).collect(Collectors.toList());

    }


    @Override
    public void deleteOrder(int id) {
        Order order = orderRepository.findById((long) id).orElse(null);
        if (order != null) {
            orderRepository.delete(order);
        }
    }



    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        Order order = orderConverter.dtoToEntity(orderDTO);
        Order updatedOrder = orderRepository.save(order);
        return orderConverter.entityToDto(updatedOrder);
    }
}