package com.example.capstone.Controllers.ClosedSystem;

import com.example.capstone.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClosedSystemController implements Initializable {

//    private BorderPane accountsView;
    public BorderPane closedSystemParent; // this is the same as the fx id in the corresponding fxml
    // the id in the fxml with this definition in the controller will allow us to reference different
    // parts of the borderpane and change them as needed, however in order to actually know when we should
    // switch to a different scene, we need to use the flag that we created inside of the ViewFactory(closed system menu?)
    // we make the flag in the menu, then set it off inside of the view factory, then we update the view in ClosedSystemController

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // below this we reference our string property flag and then add a listener to it that will pay attention to whenever
        // anything changes or happens to it, then we have a switch case on whatever the new value may be, and if it is a click on the
        // menu for the view reports page for example, then we will use the id defined in the ClsoedSystemController to switch out the middle
        // part of the border pane to the new page which we want to use, so again we use the view factory to open up the corresponding scene
        // that the user selected from the menu
        Model.getInstance().getViewFactory().getClosedSystemSelectedMenuItem().addListener((observableValue, oldVal, newVal) -> {
            switch (newVal){
                case DASHBOARD -> closedSystemParent.setCenter(Model.getInstance().getViewFactory().getDashboardView());
                case ACCOUNTS -> closedSystemParent.setCenter(Model.getInstance().getViewFactory().getAccountsView());
                default -> closedSystemParent.setCenter(Model.getInstance().getViewFactory().getViewReportsView());
            }
        });

    }

}
