package repository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import domain.Account;

public class AccountRepository {
    
    private final Map<String, Account > accountByNumber =new HashMap<>();
     public void save(Account account){
        accountByNumber.put(account.getAccountNumber(), account);
     }
     //find all accounts
        public List <Account> findAll(){
            return new ArrayList<>(accountByNumber.values());
        }
        //find account by account number
        public Optional<Account> findByNumber (String accountNumber){
            return Optional.ofNullable(accountByNumber.get(accountNumber));
        }

        public List<Account> findByCustomerId(String customerId) {
        List<Account> result = new ArrayList<>();
        for (Account a : accountByNumber.values()){
            if (a.getCustomerId().equals(customerId))
                result.add(a);
        }
        return result;
    }
       

}
