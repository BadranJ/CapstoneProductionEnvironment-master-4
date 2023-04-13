package com.example.capstone.Controllers.ClosedSystem;

import com.example.capstone.Models.Model;
import com.example.capstone.Views.ReportsCellFactory;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewReportsController implements Initializable {
    public ListView viewReports_listview;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initAllReportsList();
        viewReports_listview.setItems(Model.getInstance().getAllReports());
        viewReports_listview.setCellFactory(e -> new ReportsCellFactory());
    }

    private void initAllReportsList(){
        if(Model.getInstance().getAllReports().isEmpty()){
            Model.getInstance().setAllReports();
        }
    }
}
