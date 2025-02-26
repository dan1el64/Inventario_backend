package com.example.inventario.service.impl;

import com.example.inventario.exception.ProductNotFoundException;
import com.example.inventario.model.Product;
import com.example.inventario.repository.ProductRepository;
import com.example.inventario.service.InizializatorService;
import com.example.inventario.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService, InizializatorService {
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto con ID " + id + " no encontrado"));

        existingProduct.setName(product.getName());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setStock(product.getStock());

        return repository.save(existingProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto con ID " + id + " no encontrado"));
        repository.delete(product);
    }
    @Override
    public void seedProducts() {
        if (repository.count() == 0) {
            List<Product> products = List.of(
                    new Product("Teclado", "Accesorios", 50.0, 30),
                    new Product("Mouse", "Accesorios", 25.0, 40),
                    new Product("Monitor", "Electrónica", 200.0, 15)
            );

            for (Product p : products) {
                if (p.getName() == null || p.getName().isBlank()) {
                    System.out.println("❌ Error: El nombre del producto no puede estar vacío.");
                    return;
                }
                if (p.getCategory() == null || p.getCategory().isBlank()) {
                    System.out.println("❌ Error: La categoría del producto no puede estar vacía.");
                    return;
                }
            }

            repository.saveAll(products);
            System.out.println("✅ Productos iniciales insertados en la base de datos.");
        } else {
            System.out.println("✅ Ya existen productos, no se insertan nuevos.");
        }
    }

}
