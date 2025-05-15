
package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException; 
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * @param account 
     * @return 
     * @throws IllegalArgumentException 
     */
    public Account registerUser(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username cannot be blank");
        }

        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("Password must be at least 4 characters");
        }

        if (accountRepository.findByUsername(account.getUsername()) != null) {
            throw new DataIntegrityViolationException("Username already exists");
        }

        return accountRepository.save(account);
    }

    /**
     * @param account 
     * @return 
     * @throws SecurityException 
     */
    public Account loginUser(Account account) {
        Account existingAccount = accountRepository.findByUsername(account.getUsername());

        if (existingAccount != null && existingAccount.getPassword().equals(account.getPassword())) {
            return existingAccount;
        } else {
            throw new SecurityException("Invalid username or password");
        }
    }
}
