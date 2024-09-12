package Accounts;

import java.util.Date;


public class Transaction {
    private final int targetAccount;
    private final float transactionValue;
    private final Date transactionDate;

    public Date getTransactionDate() {
        return transactionDate;
    }

    public float getTransactionValue() {
        return transactionValue;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public int getTargetAccount() {
        return targetAccount;
    }

    private final String transactionType;

    public Transaction(int targetAccount, float transactionValue, Date transactionDate, String transactionType) {
        this.targetAccount = targetAccount;
        this.transactionValue = transactionValue;
        this.transactionDate = transactionDate;
        this.transactionType = transactionType;
    }
}
