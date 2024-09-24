package com.project.shopapp.controllers;


import com.project.shopapp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order_detail")
public class OderDetailController {
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
        return ResponseEntity.ok().body("Create new order detail: " + newOrderDetail);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail (@Valid @PathVariable long id) {
        return ResponseEntity.ok().body("Get order detail: " + id);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails (@Valid @PathVariable long orderId) {
        return ResponseEntity.ok().body("Get order detail of order with id " + orderId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail (
            @Valid @PathVariable long id,
            @RequestBody OrderDetailDTO newOrderDetail) {
        return ResponseEntity.ok().body("Update order detail: " + newOrderDetail);
    }
}
