package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @NotNull(message = "Order ID must be have")
    private long orderID;

    @JsonProperty("product_id")
    private long productID;

    @Min(value = 0, message = "price must be greater than 0")
    private float price;

    @JsonProperty("number_of_product")
    private int numberOfProduct;

    @Min(value = 0, message = "total_money must be greater than 0")
    @JsonProperty("total_money")
    private float totalMoney;


    private String color;
}
