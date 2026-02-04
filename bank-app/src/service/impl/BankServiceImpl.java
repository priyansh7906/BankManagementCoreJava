package service.impl;

import java.time.LocalDateTime;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import domain.Account;

import domain.Transaction;
import domain.Type;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository; // ✅ Correct import
import service.BankService;

public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository = new AccountRepository();
    private final TransactionRepository transactionRepository = new TransactionRepository(); // ✅ Correct spelling
    private final CustomerRepository customerRepository= new CustomerRepository(); // ✅ Correct spelling

    @Override
    public String openAccount(String name, String email, String accountType) {
        String customerId = UUID.randomUUID().toString();
        // Create customer
       
        String accountNumber = getAccountNumber();
        Account account = new Account(accountNumber, accountType, 0.0, customerId);
        accountRepository.save(account);
        return accountNumber;
    }

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public void deposit(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

        account.setBalance(account.getBalance() + amount);

        Transaction transaction = new Transaction(
                account.getAccountNumber(),
                amount,
                UUID.randomUUID().toString(),
                note,
                LocalDateTime.now(),
                Type.DEPOSIT);

        transactionRepository.add(transaction); // ✅ Works now
    }

    @Override
    public void withdraw(String accountNumber, Double amount, String note) {
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

        if (account.getBalance() < amount)
            throw new RuntimeException("Insufficient Balance");

        account.setBalance(account.getBalance() - amount);

        Transaction transaction = new Transaction(
                account.getAccountNumber(),
                amount,
                UUID.randomUUID().toString(),
                note,
                LocalDateTime.now(),
                Type.WITHDRAWAL);

        transactionRepository.add(transaction);
    }

    @Override
    public void transfer(String fromAccountNumber, String toAccountNumber, Double amount, String note) {
        if (fromAccountNumber.equals(toAccountNumber))
            throw new RuntimeException("Cannot transfer to the same account");
        Account fromAccount = accountRepository.findByNumber(fromAccountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + fromAccountNumber));
        Account ToAccount = accountRepository.findByNumber(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found: " + toAccountNumber));

        if (fromAccount.getBalance() < amount)
            throw new RuntimeException("Insufficient Balance");
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        ToAccount.setBalance(ToAccount.getBalance() + amount);

        transactionRepository.add(new Transaction(fromAccount.getAccountNumber(), amount, UUID.randomUUID().toString(),
                note, LocalDateTime.now(), Type.TRANSFER_OUT));

        transactionRepository.add(new Transaction(ToAccount.getAccountNumber(), amount, UUID.randomUUID().toString(),
                note, LocalDateTime.now(), Type.TRANSFER_IN));

    }
   @Override
public List<Transaction> getStatement(String account) {
    return transactionRepository.findByAccount(account).stream()
            .sorted(Comparator.comparing(Transaction::getDateTime))
            .collect(Collectors.toList());
} 
    @Override
    public List<Account> searchAccountsByCustomerName(String q) {
        String query = (q == null) ? "" : q.toLowerCase();
    //    List<Account> result = new ArrayList<>();
    //    for (Customer c : customerRepository.findAll()){
    //        if (c.getName().toLowerCase().contains(query))
    //            result.addAll(accountRepository.findByCustomerId(c.getCustomerId()));
    //    }
    //    result.sort(Comparator.comparing(Account::getAccountNumber));

        return customerRepository.findAll().stream()
                .filter(c -> c.getName().toLowerCase().contains(query))
                .flatMap(c -> accountRepository.findByCustomerId(c.getCustomerId()).stream())
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());

    //    return result;
    }



    private String getAccountNumber() {
        int size = accountRepository.findAll().size() + 1;
        String accountNumber = String.format("AC%06d", size);
        return accountNumber;
    }

}
