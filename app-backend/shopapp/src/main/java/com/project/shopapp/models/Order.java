package com.project.shopapp.models;

import com.project.shopapp.dtos.OrderDTO;
import com.project.shopapp.exceptions.DataNotFoundException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "fullname", length = 200)
    private String fullName;

    @Column(name = "phone_number", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "address", length = 200)
    private String address;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "note", length = 100)
    private String note;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "status", length = 100)
    private String status;

    @Column(name = "total_money")
    private float totalMoney;

    @Column(name = "shipping_address", length = 200)
    private String shippingAddress;

    @Column(name = "shipping_method", length = 100)
    private String shippingMethod;

    @Column(name = "shipping_date")
    private LocalDate shippingDate;

    @Column(name = "tracking_number", length = 200)
    private String trackingNumber;

    @Column(name = "payment_method", length = 200)
    private String paymentMethod;

    @Column(name = "active", nullable = false)
    private boolean activate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public static Order fromOrderDTO(OrderDTO orderDTO) throws Exception {
        Order newOrder = Order.builder()
                .orderDate(LocalDateTime.now())
                .email(orderDTO.getEmail())
                .activate(true)
                .address(orderDTO.getAddress())
                .note(orderDTO.getNote())
                .fullName(orderDTO.getFullName())
                .paymentMethod(orderDTO.getPaymentMethod())
                .phoneNumber(orderDTO.getPhoneNumber())
                .status("Pending")
                .shippingMethod(orderDTO.getShippingMethod())
                .shippingAddress(orderDTO.getShippingAddress())
                .totalMoney(orderDTO.getTotalMoney())
                .shippingDate(orderDTO.getShippingDate()==null ? LocalDate.now(): orderDTO.getShippingDate())
                .build();
        if(newOrder.getShippingDate().isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Shipping Date must be at least today!");
        }
        return newOrder;
    }
}
