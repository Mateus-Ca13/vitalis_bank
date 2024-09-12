package Accounts;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Account {

    private final int accountId;
    private final String name;
    private final String password;
    private float balance;
    private ArrayList<Transaction> transactionsList = new ArrayList<>(2);

    public Account(String name, String password) {
        this.password = password;
        this.name = name;
        this.balance = 1000;
        this.accountId = new Random().nextInt(1000);
    }

    public Account(String name, String password, int accountId) {
        this.password = password;
        this.name = name;
        this.balance = 1000;
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public void addTransactionToHistory(int targetAccountId, float transactionValue, Date transactionDate, String transactionType) {
        Transaction newTransaction = new Transaction(targetAccountId, transactionValue, transactionDate, transactionType);
        this.transactionsList.add(newTransaction);
    }

    public ArrayList<Transaction> viewTransactionHistory(){
        return this.transactionsList;
    }
}
