package com.example.web.repository;

import com.example.web.model.entity.Account;
import com.example.web.model.entity.Idol;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface IdolRepository extends JpaRepository<Idol,Long> {

    @Query("SELECT u FROM Idol u WHERE u.idol_name  = ?1")
    Idol findIdolByName(String name);

    @Override
    <S extends Idol> S saveAndFlush(S entity);

    @Override
    void deleteById(Long id);

    @Override
    List<Idol> findAll();

    @Query("SELECT u FROM Idol u WHERE u.idol_id = ?1")
    Idol findIdolById(long id);

    @Transactional
    @Modifying
    @Query("update Idol u set u.idol_name = ?1, u.createddate = ?2 where u.idol_id = ?3")
    Integer updateIdol(String name, Timestamp date, long id);
}
