package com.example.web.repository;

import com.example.web.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Override
    <S extends Product> S saveAndFlush(S entity);

    @Query(value="SELECT o FROM Product o WHERE o.product_id = ?1 and o.product_name = ?2")
    Product findByProduct_idAndProduct_name(Long id, String name);

    @Query(value="SELECT count(*) FROM Product")
    int getCountProduct();

    Page<Product> findAll(Pageable pageable);

    @Query(value="SELECT o FROM Product o WHERE o.idol.idol_id = ?1")
    Page<Product> findByIdol_id(Integer idolId, Pageable pageable);

    @Query(value="SELECT o FROM Product o WHERE o.product_name LIKE ?1")
    Page<Product> findByProduct_name(String nameProduct, Pageable pageable);

}
