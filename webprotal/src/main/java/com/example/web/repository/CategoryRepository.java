package com.example.web.repository;

import com.example.web.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT u FROM Category u WHERE u.category_name  = ?1")
    Category findCateByName(String name);

    @Override
    <S extends Category> S saveAndFlush(S entity);

    @Override
    void deleteById(Long id);

    @Override
    List<Category> findAll();

    @Query("SELECT u FROM Category u WHERE u.category_id = ?1")
    Category findCateById(long id);

    @Transactional
    @Modifying
    @Query("update Category u set u.category_name = ?1, u.createddate = ?2 where u.category_id = ?3")
    Integer updateCategory(String name, Timestamp date, long id);

}

