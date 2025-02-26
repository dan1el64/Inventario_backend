package com.example.inventario.controller;

import com.example.inventario.dto.ApiResponse;
import com.example.inventario.model.Product;
import com.example.inventario.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Ejemplo", description = "Ejemplo de API con Swagger")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los productos")
    public ResponseEntity<ApiResponse<List<Product>>> getAllProducts() {
        log.info("Obteniendo todos los productos");
        List<Product> products = service.getAllProducts();
        return ResponseEntity.ok(new ApiResponse<>(true, "Productos obtenidos", products));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo producto")
    public ResponseEntity<ApiResponse<Product>> createProduct(@RequestBody Product product) {
        log.info("Creando producto: {}", product.getName());
        Product savedProduct = service.createProduct(product);
        return ResponseEntity.status(201).body(new ApiResponse<>(true, "Producto creado", savedProduct));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto por ID")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        log.info("Actualizando producto con ID: {}", id);
        Product updatedProduct = service.updateProduct(id, product);
        return ResponseEntity.ok(new ApiResponse<>(true, "Producto actualizado", updatedProduct));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto por ID")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        log.info("Eliminando producto con ID: {}", id);
        service.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Producto eliminado", null));
    }
}
