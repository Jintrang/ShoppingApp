package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDTO order,
                                                    BindingResult result
    ) {
        if (result.hasErrors()) {
            List<String> errorsMessage = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorsMessage);
        }
        return ResponseEntity.ok().body("Create order successful: " + order);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") long userID) {
        try {
            return ResponseEntity.ok().body("Order with id = " + userID + " successful");
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
        return ResponseEntity.ok().body("Create order successful: " + order);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable("id") long id) {
        return ResponseEntity.ok().body("Delete order successful: " + id);
    }
}
