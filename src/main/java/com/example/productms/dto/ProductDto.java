package com.example.productms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;

    @NotBlank(message = "Product name is mandatory")
    private String name;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be a positive number")
    private double price;

    @NotNull(message = "Product count is required")
    @Min(value = 0, message = "Count must be a positive number")
    private int count;
}
