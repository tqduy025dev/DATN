package com.server.datn.server.services.impl;

import com.server.datn.server.entity.account.Account;
import com.server.datn.server.repositories.AccountRepository;
import com.server.datn.server.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }
}
