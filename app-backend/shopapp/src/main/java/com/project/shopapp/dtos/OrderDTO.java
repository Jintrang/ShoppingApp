package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    @JsonProperty("user_id")
    @NotBlank(message = "user_id is null")
    private String userID;

    @JsonProperty("full_name")
    private String fullName;


    @NotBlank(message = "address is null")
    private String address;

    @JsonProperty("phone_number")
    @NotBlank(message = "phoneNumber is null")
    @Min(value=10, message = "phoneNumber is at least 10")
    private String phoneNumber;

    private String email;

    private String note;

    @JsonProperty("total_money")
    private Float totalMoney;

    @JsonProperty("shipping_address")
    @NotBlank(message = "shippingAddress is null")
    private String shippingAddress;

    @JsonProperty("shipping_method")
    @NotBlank(message = "shippingMethod is null")
    private String shippingMethod;

    @JsonProperty("payment_method")
    @NotBlank(message = "paymentMethod is null")
    private String paymentMethod;

}
