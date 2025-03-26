package org.praveen.productservice.services;

import java.util.List;
import java.util.Optional;

import org.praveen.productservice.exceptions.ProductNotFoundException;
import org.praveen.productservice.models.Product;
import org.praveen.productservice.models.Category;
import org.praveen.productservice.repositories.CategoryRepository;
import org.praveen.productservice.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("selfProductService")
//@Primary
public class SelfProductService implements ProductService{
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    public SelfProductService(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }
    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findProductById(id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("Product Not found id = ",id);
        }
        return product.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber,int pageSize,String sortField) {
        return productRepository.findAll(PageRequest.of(pageNumber,pageSize, Sort.by("price").ascending()));
    }

    @Override
    public List<Product> getAllProductsByCategory(Long id) {
        return productRepository.getProductsByTitleSQL(id);
    }

    @Override
    public ResponseEntity<Product> replaceProduct(Long id, Product product) {
        return null;
    }

    @Override
    public ResponseEntity<List<Product>> getCategoryByName(String name) {
        Category category = categoryRepository.findCategoryByTitle(name);
        List<Product> products = productRepository.getProductsByCategory(category.getId());
        ResponseEntity<List<Product>> responseEntity = new ResponseEntity<>(products, HttpStatus.OK);

        return responseEntity;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteProduct() {

    }

    @Override
    public Product createProduct(Product product) {
        Category category = product.getCategory();
        if(category.getId() == null){
            Category savedCategory = categoryRepository.save(category);
            product.setCategory(savedCategory);
        }
        else{
            Optional<Category> newCategory = categoryRepository.findById(category.getId());
            product.setCategory(newCategory.get());
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }
}
