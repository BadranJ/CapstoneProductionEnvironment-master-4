package com.example.capstone.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class CheckingAccount extends Account {
    private final IntegerProperty transactionLimit; // the max number of transactions per client per day

    public CheckingAccount(String owner, String accountNumber, Double balance, int tlimit){
        super(owner, accountNumber, balance);
        this.transactionLimit = new SimpleIntegerProperty(this, "TransactionLimit", tlimit);
    }

    public IntegerProperty transactionLimitProperty() {
        return transactionLimit;
    }


}
