package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.models.Order;
import com.project.shopapp.services.IOrderService;
import com.project.shopapp.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    public final OrderService orderService;
    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO orderDTO,
                                                    BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errorsMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        Order newOrder = null;
        try {
            newOrder = orderService.createOrder(orderDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().body(newOrder);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") long userID) {
        try {
            List<Order> orders = orderService.getOrderByUserId(userID);
            return ResponseEntity.ok().body(orders);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") long iD) {
        try {
            Order order = orderService.getOrderById(iD);
            return ResponseEntity.ok().body(order);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable("id") long id,
                                         @Valid @RequestBody OrderDTO order,
                                         BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorsMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        try {
            Order existingOrder = orderService.updateOrder(id, order);

            return ResponseEntity.ok().body("update successfully: " + order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable("id") long id) {
        try {
            orderService.deleteOrderById(id);
            return ResponseEntity.ok().body("Delete order successful: " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
