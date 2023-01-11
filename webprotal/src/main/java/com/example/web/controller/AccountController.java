package com.example.web.controller;

import com.example.web.business.AccountHelper;
import com.example.web.model.bo.AccountBO;
import com.example.web.model.entity.Account;
import com.example.web.model.entity.Idol;
import com.example.web.model.entity.Product;
import com.example.web.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apiaccount")
public class AccountController {

    @Autowired
    AccountHelper helper;

    @GetMapping(value = "/getaccountbyname")
    public ResponseEntity<Account> getAccountByName(String name) {
        Account result = null;
        try {
            result = helper.getAccountByName(name);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/insertacccount")
    public ResponseEntity<Long> insertAccount(@RequestBody AccountBO account) {
        Long result = null;
        try {
            result = helper.insertAccount(account);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @PutMapping(value = "/updateaccount")
    public ResponseEntity<Long> updateaccount(@RequestBody AccountBO account) {
        Long result = null;
        try {
            result = helper.updateAccount(account);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/deleteacccount")
    public ResponseEntity<Long> deleteAccount(Long id) {
        Long result = null;
        try {
            result = helper.deletedAccount(id);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(result);
    }

}
