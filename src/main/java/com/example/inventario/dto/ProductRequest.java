package com.example.inventario.dto;

import com.example.inventario.model.Product;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotBlank String name,
        @NotBlank String category,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) Double price,
        @NotNull @Min(0) Integer stock) {
    public Product toProduct() {
        return new Product(name.trim(), category.trim(), price, stock);
    }
}
