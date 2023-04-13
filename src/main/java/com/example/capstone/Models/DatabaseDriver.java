package com.example.capstone.Models;
// this connects to the database, and defines the functions we want to use in the model

import java.sql.*;

// if we did this in the model class instead, the model object would get too big so we are doing it here instead
public class DatabaseDriver {
    private Connection conn;

    public DatabaseDriver(){
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:mazebank.db"); // this defines where and what type of db we are connecting
            // will need to change the path above when we change the db
        } catch (SQLException e){
            e.printStackTrace(); // it would be better to make another error factory to display different errors
        }
    }

    // closed system section
// this is how we fetch the data below those are all SQL methods (java.sql)
    public ResultSet getClosedSystemData(String pAddress, String password){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Clients WHERE PayeeAddress ='"+ pAddress + "' AND Password ='"+ password+"';");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getReportData(String pAddress, int limit){
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("select * from Transactions where Sender ='"+pAddress+"' or Receiver ='"+pAddress+"' limit "+limit+";");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return resultSet;
    }







    // open system section










    // utility methods (for both)

}
