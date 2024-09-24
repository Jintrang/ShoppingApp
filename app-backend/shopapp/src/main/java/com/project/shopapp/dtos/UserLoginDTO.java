package com.project.shopapp.dtos;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is null")
    private String phoneNumber;

    @NotBlank(message = "Password is null")
    private String password;
}
