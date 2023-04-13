package com.example.capstone;

import com.example.capstone.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        // this below initializes the login window, and then from the login window we can see that once
        // the login controller is initialized, the Closed System Menu is then initialized from the login window in the same manner from the singleton
        Model.getInstance().getViewFactory().showLoginWindow(); // using singleton pattern from Model class and view factory

    }
}

