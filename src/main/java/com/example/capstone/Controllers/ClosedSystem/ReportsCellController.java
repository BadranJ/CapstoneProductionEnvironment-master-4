package com.example.capstone.Controllers.ClosedSystem;

import com.example.capstone.Models.Reports;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsCellController implements Initializable {

    public Line lineReport_one;
    public Line lineReport_two;
    public Label report_date_label;
    public Label sender_label;
    public Label receiver_label;
    public Label amount_label;

    private final Reports report; // this is used with the reportscellfactory and the ReportsCellController, each individual report

    public ReportsCellController(Reports report){
        this.report = report; // basically this is the constructor for the private final reports above, and this
        // allows us to construct it as a public reportcellcontroller type instead because all of this is supposed to
        // be to replace our custom made controller, and use the builtin javafx listview controller to update the list cells
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sender_label.textProperty().bind(report.senderProperty());
        receiver_label.textProperty().bind(report.receiveProperty());
        amount_label.textProperty().bind(report.amountProperty().asString());
        report_date_label.textProperty().bind(report.dateProperty().asString());

    }


}
