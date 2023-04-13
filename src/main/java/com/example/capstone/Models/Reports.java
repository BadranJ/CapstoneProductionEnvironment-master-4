package com.example.capstone.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Reports {
    private final StringProperty sender; // this is a way to define and keep track of the usernames of who created the report
    private final StringProperty receiver;
    private final DoubleProperty amount;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty message;

    public Reports(String sender, String receiver, double amount, LocalDate date, String message){
// it is important to not only use strings or whatever else, but later we need to bind these to JavaFX observable properties
        this.sender = new SimpleStringProperty(this, "sender", sender);
        this.receiver = new SimpleStringProperty(this, "Receiver", receiver);
        this.amount = new SimpleDoubleProperty(this, "Amount", amount);
        this.date = new SimpleObjectProperty<>(this, "Date", date);
        this.message = new SimpleStringProperty(this, "Message", message);
    }

    public StringProperty senderProperty () {
        return this.sender; // it is important that this only returns the property itself and not this.sender.get() which would return the value
    }

    public StringProperty receiveProperty(){return this.receiver;}

    public DoubleProperty amountProperty(){return this.amount;}

    public ObjectProperty<LocalDate> dateProperty() {
        return this.date;
    }

    public StringProperty messageProperty(){return this.message;} // this would be the message attached to the transaction for the bank shell
}
