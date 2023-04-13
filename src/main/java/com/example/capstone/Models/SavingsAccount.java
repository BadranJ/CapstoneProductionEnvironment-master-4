package com.example.capstone.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class SavingsAccount extends Account{

    private final DoubleProperty withdrawalLimit; // withdrawal limit from savings

    public SavingsAccount(String owner, String accountNumber, Double balance, int wlimit){
        super(owner, accountNumber, balance);
        this.withdrawalLimit = new SimpleDoubleProperty(this, "WithdrawalLimit", wlimit);
    }

    public DoubleProperty withdrawalLimitProperty() {
        return withdrawalLimit;
    }
}
