package org.praveen.productservice.repositories;

import org.praveen.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findProductById(Long id);

    List<Product> findProductByTitle(String title);

    List<Product> findProductByCategoryId(Long category_id);

    @Query("select p from Product p where p.category.id = :id")
    List<Product> getProductsByCategory(@Param("id") Long id);

    @Query(value = "select * from product as p  where p.id = :id", nativeQuery = true)
    List<Product> getProductsByTitleSQL(@Param("id") Long id);

    @Override
    Page<Product> findAll(Pageable pageable);
}
