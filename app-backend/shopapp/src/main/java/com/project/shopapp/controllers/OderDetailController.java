package com.project.shopapp.controllers;


import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order_detail")
@RequiredArgsConstructor
public class OderDetailController {
    public final OrderDetailService orderDetailService;
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail (
            @Valid @RequestBody OrderDetailDTO newOrderDetail,
            BindingResult result
            ) {
        if (result.hasErrors()) {
            List<String> errorsMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        try {
            OrderDetail orderDetail = orderDetailService.createOrderDetail(newOrderDetail);
            return ResponseEntity.ok().body("Create new order detail: " + orderDetail);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail (@Valid @PathVariable long id) {
        try {
            OrderDetail orderDetail = orderDetailService.getOrderDetailById(id);
            return ResponseEntity.ok().body(orderDetail);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails (@Valid @PathVariable long orderId) {
        try {
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetailByOrderId(orderId);
            return ResponseEntity.ok().body(orderDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail (
            @Valid @PathVariable long id,
            @RequestBody OrderDetailDTO newOrderDetail) {
        try {
            OrderDetail orderDetail = orderDetailService.updateOrderDetail(id, newOrderDetail);
            return ResponseEntity.ok().body(orderDetail);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
