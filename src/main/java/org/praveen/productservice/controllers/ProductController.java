package org.praveen.productservice.controllers;

import org.praveen.productservice.commons.AuthCommons;
import org.praveen.productservice.dtos.UserDto;
import org.praveen.productservice.exceptions.ProductNotFoundException;
import org.praveen.productservice.models.Category;
import org.praveen.productservice.models.Product;
import org.praveen.productservice.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private AuthCommons authCommons;
    private ProductService productService;
    public ProductController(@Qualifier("selfProductService") ProductService productService, AuthCommons authCommons){
        this.productService = productService;
        this.authCommons = authCommons;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        //UserDto userDto = authCommons.validateToken(token);
        ResponseEntity<Product> responseEntity;
//        if(userDto == null){
//            responseEntity = new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
//            return responseEntity;
//        }
        Product product = productService.getProductById(id);

        responseEntity = new ResponseEntity<>(product, HttpStatus.OK);
        return responseEntity;
    }
    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/cat/{categoryId}")
    public List<Product> getCategoryById(@PathVariable("categoryId")  Long categoryId){
        return productService.getAllProductsByCategory(categoryId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable("id") Long id,@RequestBody Product product){
        return productService.replaceProduct(id,product);
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<List<Product>> getCategoryByName(@PathVariable("name") String name){
        return productService.getCategoryByName(name);
    }
    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return productService.getAllCategories();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }
}
