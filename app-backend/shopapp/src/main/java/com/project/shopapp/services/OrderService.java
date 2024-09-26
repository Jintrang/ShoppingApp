package com.project.shopapp.services;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.OrderRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    public final OrderRepository orderRepository;
    public final UserRepository userRepository;
    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        User user = userRepository.findById(orderDTO.getUserID())
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        Order newOrder = Order.fromOrderDTO(orderDTO);
        newOrder.setUser(user);
        orderRepository.save(newOrder);
        return newOrder;
    }

    @Override
    public Order updateOrder(long orderId, OrderDTO orderDTO) throws Exception {
        orderRepository.findById(orderId).orElseThrow(() -> new DataNotFoundException("Order not found"));
        Order updatedOrder = Order.fromOrderDTO(orderDTO);
        orderRepository.save(updatedOrder);
        return updatedOrder;
    }

    @Override
    public Order getOrderById(long id) throws Exception {
        //Order order =
        return orderRepository.findById(id).orElseThrow(()-> new DataNotFoundException("Order not found"));
    }

    @Override
    public List<Order> getOrderByUserId(long userId) throws Exception {
        userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
        return orderRepository.findByUserId(userId);
    }

    @Override
    public void deleteOrderById(long id) throws Exception {
        Order existingOrder = getOrderById(id);
        existingOrder.setActivate(false);
        orderRepository.save(existingOrder);
    }
}
