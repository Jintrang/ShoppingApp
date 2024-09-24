package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "full_name", length = 200)
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
    private Date shippingDate;

    @Column(name = "tracking_number", length = 200)
    private String trackingNumber;

    @Column(name = "payment_method", length = 200)
    private String paymentMethod;

    @Column(name = "activate", nullable = false)
    private boolean activate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
