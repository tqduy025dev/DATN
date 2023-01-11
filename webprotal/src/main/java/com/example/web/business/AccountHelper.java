package com.example.web.business;

import com.example.web.model.bo.AccountBO;
import com.example.web.model.entity.Account;
import com.example.web.repository.AccountRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerResponse;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.sql.Timestamp;
import java.util.Date;

@Service
public class AccountHelper {
    @Autowired
    private AccountRepository repository;


    public Account getAccountByName(String name) {
        var result = repository.findAllActiveUsers(name);
        return result != null ? result : null;
    }

    public long insertAccount(AccountBO bo) {
        Account account = new Account();
        account.setPassword(bo.getPassword());
        account.setUser_name(bo.getUser_name());
        var date = new Date();
        account.setCreateddate( new Timestamp(date.getTime()));


        var result = repository.save(account);

        return result != null ? result.getUser_id() : -1;
    }

     public long deletedAccount(Long id){
         repository.deleteById(id);
         return 1;
     }

     public long updateAccount(AccountBO bo){
            var date = new Date();
            var tmp = new Timestamp(date.getTime());
            var rs = repository.updateAccount(bo.getUser_name(),bo.getPassword(),tmp,bo.getUser_id());
            return rs ;
     }

}
