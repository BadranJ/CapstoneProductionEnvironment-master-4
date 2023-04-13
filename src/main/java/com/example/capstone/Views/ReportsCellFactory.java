package com.example.capstone.Views;

import com.example.capstone.Controllers.ClosedSystem.ReportsCellController;
import com.example.capstone.Models.Reports;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class ReportsCellFactory extends ListCell<Reports> {


    @Override
    protected void updateItem(Reports reports, boolean empty) { // this is a built in method for the ListCell class
        super.updateItem(reports, empty); // that is why we are passing this to the super class here, it is a prebuilt one that allows us to update our list cells
        if(empty || reports == null){
            setText(null);
            setGraphic(null);
        }
        else{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ClosedSystem/ReportsCell.fxml"));
            ReportsCellController controller = new ReportsCellController(reports);
            loader.setController(controller);
            setText(null);

            try {
                setGraphic(loader.load()); // we are using all of this class as a controller and this is where we actually load the page
            } catch (Exception e){
                e.printStackTrace(); // need some different error catching here at some point
            }
        }
    }
    // in everything above whenever we get passed each specific report through the list view, we then have to think
    // about how to update the controls and different data pieces, that is why we are passing the specific report in to the ReportsCellController
    // because then within that class, we are going to do many different things to manipulate and update that data

}
