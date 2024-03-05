package fintech.model;

import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

public class Account {
    private TreeMap<Long, Transaction> transactions = new TreeMap<>();
    private final String owner;
    private String name;
    private double balance = 0.0;

    public Account(String owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addTransaction(Transaction transaction) {
        long currentTimeMillis = System.currentTimeMillis();
        transactions.put(currentTimeMillis, transaction);
        balance += transaction.getAmount();
    }

    public List<Transaction> getTransactions() {
        TreeMap<Long, Transaction> transactionMap = new TreeMap<>();
        transactionMap.putAll(transactions);
        List<Transaction> transactionList = new ArrayList<>(transactionMap.values());
        return transactionList;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }

    public void removeBalance(double amount) {
        this.balance -= amount;
    }

    @Override
    public String toString() {
        return name + "|" + owner + "|" + String.format("%.1f", balance);
    }

    public void setName(String name2) {
        this.name = name2;
    }
}
