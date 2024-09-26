package com.project.shopapp.services;


import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.models.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;
    Order updateOrder(long orderID,OrderDTO orderDTO) throws Exception;
    Order getOrderById(long id) throws Exception;
    List<Order> getOrderByUserId(long userId) throws Exception;
    void deleteOrderById(long id) throws Exception;
}
