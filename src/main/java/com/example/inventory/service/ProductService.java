package com.example.inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.inventory.dto.ProductDTO; // Import the ProductDTO class
import com.example.inventory.entity.Product;
import com.example.inventory.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Add a new product
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Update product stock
    public Product updateProductStock(Long productId, int newStock) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setStock(newStock);
            return productRepository.save(product);
        }
        return null; // Handle error if product not found
    }

    // View product details by ID
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Process an order (update stock after an order is processed)
    public boolean processOrder(Long productId, int quantity) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            if (product.getStock() >= quantity) {
                product.setStock(product.getStock() - quantity);
                productRepository.save(product);
                return true;
            }
        }
        return false; // Handle insufficient stock or product not found
    }

    // Convert Product to ProductDTO
    public ProductDTO convertToDTO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setStock(product.getStock());
        // Set other fields as necessary
        return dto;
    }
}
