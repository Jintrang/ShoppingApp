package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @JsonProperty("full_name")
    private String fullName;


    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number is null")
    private String phoneNumber;


    private String address;


    @NotBlank(message = "Password is null")
    private String password;


    @NotBlank(message = "Re password is null")
    @JsonProperty("re_password")
    private String rePassword;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;

    @JsonProperty("role_id")
    private long roleId;
}
