package com.project.shopapp.models;

import com.project.shopapp.dtos.OrderDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_details")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "number_of_products", nullable = false)
    private int numberOfProducts;

    @Column(name = "total_money", nullable = false)
    private float totalMoney;

    @Column(name = "color", length = 20)
    private String color;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

}
