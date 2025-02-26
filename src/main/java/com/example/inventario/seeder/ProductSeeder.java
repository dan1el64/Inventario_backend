package com.example.inventario.seeder;

import com.example.inventario.service.InizializatorService;
import com.example.inventario.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProductSeeder implements CommandLineRunner {

    private final InizializatorService inizializatorService;

    public ProductSeeder(ProductService productService) {
        this.inizializatorService = (InizializatorService) productService;
    }

    @Override
    public void run(String... args) {
        inizializatorService.seedProducts();
    }
}
