package com.project.shopapp.dtos;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

@Data //toString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
    @NotEmpty(message = "Name isn't empty")
    private String name;
}
