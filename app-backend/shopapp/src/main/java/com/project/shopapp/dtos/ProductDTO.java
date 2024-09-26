package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    @NotBlank(message = "Title not blank")
    @Size(min=3, max =200, message = "Size must be 3 to 200 characters")
    private String name;
    private Float price;
    private String thumbnail;
    private String description;
    @JsonProperty("category_id")
    private Long categoryId;
    private List<MultipartFile> files;
}
