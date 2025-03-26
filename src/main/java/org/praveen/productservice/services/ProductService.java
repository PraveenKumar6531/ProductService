package org.praveen.productservice.services;

import org.praveen.productservice.exceptions.ProductNotFoundException;
import org.praveen.productservice.models.Category;
import org.praveen.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id) throws ProductNotFoundException;

    List<Product> getAllProducts();

    Page<Product> getAllProducts(int page, int size, String sortField);

    List<Product> getAllProductsByCategory(Long id);

    ResponseEntity<Product> replaceProduct(Long id,Product product);

    ResponseEntity<List<Product>> getCategoryByName(String name);

    List<Category> getAllCategories();

    void deleteProduct();

    Product createProduct(Product product);

    Product updateProduct(Long id,Product product);
}
