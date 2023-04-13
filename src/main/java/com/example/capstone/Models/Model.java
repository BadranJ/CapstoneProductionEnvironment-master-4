package com.example.capstone.Models;

import com.example.capstone.Views.SystemType;
import com.example.capstone.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Model {
// the sqlite_sequence table in our sqlite table is a sequencing that helps us with auto increment or anything else we want to do automatically
    private static Model model;
    private final ViewFactory viewFactory; // we are going to implement a singleton design pattern on the view factory here
    private final DatabaseDriver databaseDriver; // our databasedriver instance

    private SystemType loginAccountType = SystemType.CLOSED_SYSTEM; // we auto set the system type to closed system when the user logs in

    // Closed System Data Section
    private final ClosedSystem closedSystem;
    private boolean closedSystemLoginSuccessFlag; // this is true if the closedSystem user passes the credentials check
    private final ObservableList<Reports> latestReports;
    private final ObservableList<Reports> allReports;

    // Open System Data Section

    private Model(){ // private constructor holding view factory instantiation

        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();

        // Closed System Data Section
        this.closedSystemLoginSuccessFlag = false;
        this.closedSystem = new ClosedSystem("", "", "", null, null, null);
        this.latestReports = FXCollections.observableArrayList();
        this.allReports = FXCollections.observableArrayList();
        // Open System Data Section
    }

    public static synchronized Model getInstance(){
        if(model == null){ // when we do it this way, this method is the only public method that has access
            // to the view factory instantiation, the model objects only job is to hold this instantiation,
            // therefore we can treat this model class itself as a protected instantiation of the view factory

            // because the constructor is private we have to construct it here within the class, but this makes it
            // so that no matter where we call upon the view factory from in our application we are always getting the same object instantiation

            model = new Model();
        }
        return model;
    }
// this makes sure that the data is consistent throughout the application

    // one issue with this is that the singleton can become incredibly large and unreadable
    // when this happens we will need to take stuff out that we can from this section
    public ViewFactory getViewFactory(){
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {return databaseDriver;}

// we could also use preconstructed controllers which has access to the controller object of each fxml file
    // then through the constructor you can pass the data because you have control of the creation of the controller, and pass relavant data as needed

    public SystemType getLoginAccountType() {
        return loginAccountType;
    }

    public void setLoginAccountType(SystemType loginAccountType) {
        this.loginAccountType = loginAccountType;
    }

    // Closed System Method Section


    public boolean getClosedSystemLoginSuccessFlag() {return this.closedSystemLoginSuccessFlag;}
    public void setClosedSystemLoginSuccessFlag(boolean flag){this.closedSystemLoginSuccessFlag = flag;}


    public ClosedSystem getClosedSystem() {
        return closedSystem;
    }


    public void evaluateClosedSystemCredentials(String pAddress, String password){
        CheckingAccount checkingAccount;
        SavingsAccount savingsAccount;

        ResultSet resultSet = databaseDriver.getClosedSystemData(pAddress, password); // this is going to return the database results
        // that we specified in the query of this method above in the databasedriver class
        try {
            // we first need to check if there is any data at all, the query has ands
            if(resultSet.isBeforeFirst()){ // checks if the cursor at first row is at the last, if not empty then there is data
                this.closedSystem.firstNameProperty().set(resultSet.getString("FirstName")); // getting the value with this column name
                this.closedSystem.lastNameProperty().set(resultSet.getString("LastName"));
                this.closedSystem.payeeAddressProperty().set(resultSet.getString("PayeeAddress"));

                // below we are getting the date which we need to convert from a SQL format to a Java date object
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                this.closedSystem.dateCreatedProperty().set(date);



                this.closedSystemLoginSuccessFlag = true; // if we get this far and we know the database returned soemthing, then we know they were a valid user
            }



        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void prepareReports(ObservableList<Reports> report, int limit){
        ResultSet resultSet = databaseDriver.getReportData(this.closedSystem.payeeAddressProperty().get(), limit);
        try{
            while (resultSet.next()){
                String sender = resultSet.getString("Sender");
                System.out.println(sender);
                String receiver = resultSet.getString("Receiver");
                System.out.println(receiver);
                double amount = resultSet.getDouble("Amount");
                System.out.println(amount);
                String[] dateParts = resultSet.getString("Date").split("-");
                LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                System.out.println(date);
                String message = resultSet.getString("Message");
                System.out.println(message);
                report.add(new Reports(sender, receiver, amount, date, message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLatestReports(){
        prepareReports(this.latestReports, 4);
    }

    public ObservableList<Reports> getLatestReports(){
        return latestReports;
    }

    public void setAllReports(){
        prepareReports(this.allReports, -1); //making limit -1 returns all transactions
    }

    public ObservableList<Reports> getAllReports() {
        return allReports;
    }
}
