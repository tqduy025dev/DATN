package com.example.web.repository;

import com.example.web.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT u FROM Account u WHERE u.user_name  = ?1")
    Account findAllActiveUsers(String name);

    @Override
     Account  saveAndFlush(Account entity);

    @Override
    void deleteById(Long id);


    @Transactional
    @Modifying
    @Query("update Account u set u.user_name = ?1, u.password = ?2, u.createddate = ?3 where u.user_id = ?4")
    Integer updateAccount(String name,String pass, Timestamp date, long id);


}
