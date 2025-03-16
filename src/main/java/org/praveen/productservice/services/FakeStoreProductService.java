package org.praveen.productservice.services;

import org.praveen.productservice.dtos.FakeStoreServiceProductDTO;
import org.praveen.productservice.exceptions.ProductNotFoundException;
import org.praveen.productservice.models.Category;
import org.praveen.productservice.models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService{
    private RestTemplate restTemplate;
    public FakeStoreProductService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    private Product convertFakeStoreProductDTO(FakeStoreServiceProductDTO fakeStoreServiceProductDTO){
        Product product = new Product();
        product.setId(fakeStoreServiceProductDTO.getId());
        product.setTitle(fakeStoreServiceProductDTO.getTitle());
        product.setPrice(fakeStoreServiceProductDTO.getPrice());
        product.setImage(fakeStoreServiceProductDTO.getImage());
        product.setDescription(fakeStoreServiceProductDTO.getDescription());

        Category category = new Category();
        category.setTitle(fakeStoreServiceProductDTO.getCategory());

        product.setCategory(category);

        return product;
    }
    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        FakeStoreServiceProductDTO fakeStoreServiceProductDTO = restTemplate.getForObject("https://fakestoreapi.com/products/"+id, FakeStoreServiceProductDTO.class);
        if(fakeStoreServiceProductDTO == null){
            throw new ProductNotFoundException("Product not found",id);
        }
        return convertFakeStoreProductDTO(fakeStoreServiceProductDTO);
    }

    @Override
    public List<Product> getAllProducts() {
        //int x = 1/0;
        FakeStoreServiceProductDTO[] fakeStoreServiceProductDTOS = restTemplate.getForObject("https://fakestoreapi.com/products",FakeStoreServiceProductDTO[].class);
        List<Product> productList = new ArrayList<>();
        if(fakeStoreServiceProductDTOS == null){
            return null;
        }
        for(FakeStoreServiceProductDTO fakeStoreServiceProductDTO : fakeStoreServiceProductDTOS){
            productList.add(convertFakeStoreProductDTO(fakeStoreServiceProductDTO));
        }
        return productList;
    }

    @Override
    public List<Product> getAllProductsByCategory(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Product> replaceProduct(Long id,Product product) {
        FakeStoreServiceProductDTO fakeStoreServiceProductDTO = new FakeStoreServiceProductDTO();
        fakeStoreServiceProductDTO.setDescription(product.getDescription());
        fakeStoreServiceProductDTO.setTitle(product.getTitle());
        fakeStoreServiceProductDTO.setPrice(product.getPrice());
        fakeStoreServiceProductDTO.setImage(product.getImage());
        fakeStoreServiceProductDTO.setCategory(product.getCategory().getTitle());
        RequestCallback requestCallback
                = restTemplate.httpEntityCallback(fakeStoreServiceProductDTO, FakeStoreServiceProductDTO.class);
        HttpMessageConverterExtractor<FakeStoreServiceProductDTO> responseExtractor
                = new HttpMessageConverterExtractor<>(FakeStoreServiceProductDTO.class, restTemplate.getMessageConverters());
        FakeStoreServiceProductDTO response;
        response = restTemplate.execute("https://fakestoreapi.com/products/"+id, HttpMethod.PUT, requestCallback, responseExtractor);
        ResponseEntity<Product> responseEntity;
        if(response == null){
            return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
        }
        Product updatedProduct = convertFakeStoreProductDTO(response);
        responseEntity = new ResponseEntity<Product>(updatedProduct,HttpStatus.OK);
        return responseEntity;
    }

    @Override
    public ResponseEntity<List<Product>> getCategoryByName(String name) {
        FakeStoreServiceProductDTO[] fakeStoreServiceProductDTOS
                = restTemplate.getForObject("https://fakestoreapi.com/products/category/"+name,FakeStoreServiceProductDTO[].class);
        if(fakeStoreServiceProductDTOS == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<Product> productList = new ArrayList<>();
        for(FakeStoreServiceProductDTO response : fakeStoreServiceProductDTOS){
            productList.add(convertFakeStoreProductDTO(response));
        }
        return new ResponseEntity<>(productList,HttpStatus.OK);
    }

    @Override
    public List<Category> getAllCategories() {
        String[] categories
                = restTemplate.getForObject("https://fakestoreapi.com/products/categories",String[].class);
        List<String> categoryList = new ArrayList<>();
        categoryList.add(Arrays.toString(categories));
        return null;
    }

    @Override
    public void deleteProduct() {

    }

    @Override
    public Product createProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }
}
