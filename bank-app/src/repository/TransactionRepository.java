package repository;

import java.util.*;
import domain.Transaction;

public class TransactionRepository {

    // 🧠 Step 2 — Add this field at the top
    private final Map<String, List<Transaction>> txByAccount = new HashMap<>();

    // 🧩 Step 3 — Add transaction method
    public void add(Transaction transaction) {
        // if the account doesn't exist, create a new list
        txByAccount
            .computeIfAbsent(transaction.getAccountNumber(), k -> new ArrayList<>())
            .add(transaction);
    }

    // 🧩 Step 4 — Find transactions by account
    public List<Transaction> findByAccount(String account) {
        return new ArrayList<>(txByAccount.getOrDefault(account, Collections.emptyList()));
    }
}
