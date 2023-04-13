package com.example.capstone.Controllers.ClosedSystem;

import com.example.capstone.Models.Model;
import com.example.capstone.Views.ClosedSystemMenuOptions;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

// the way it will work is that this will stay on the left as a nav bar almost
// then in the middle we will have a borderpane that can update it's center property
// borderpane has four sections top, right, bottom, left, and center, we can update any of them
// so the center will be what we are chaning
public class ClosedSystemMenuController implements Initializable {
    public Button dashboard_btn;
    public Button view_reports_button;
    public Button accounts_button;
    public Button profile_button;
    public Button logout_button;
    public Button report_bug_button;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListeners(); // basically every time that the flag inside of the view factory changes we have a listener already
        // added inside of the ClosedSystemController class, and this listener pays attention to what the new value selected is
        // and then using the switch statement again inside of the closedsystemcontroller class, we use that switch statement to set
        // the center of the borderpane to something else, however the actual change is done through the menuController (here)
        // where when these buttons are actually pressed, it will set the switch statement to a different case
        // and that case will correspond to what the general closedSystemController will actually open from the
        // view factory instances
    }

    private void addListeners(){ // when the dashboard button is clicked, we trigger the dashboard object from facotry
        dashboard_btn.setOnAction(event -> onDashboard());
        view_reports_button.setOnAction(event -> onViewReports());
        accounts_button.setOnAction(event -> onAccounts());
        logout_button.setOnAction(event -> onLogout());
    }

    private void onDashboard(){ // this sets the flag for which item we have selected in the factory to dashboard essentially
        Model.getInstance().getViewFactory().getClosedSystemSelectedMenuItem().set(ClosedSystemMenuOptions.DASHBOARD);
    }

    private void onViewReports(){ // we must make sure that these values in these methods for the set match the switch case exactly
        // in the closedsystemcontroller class switch case
        Model.getInstance().getViewFactory().getClosedSystemSelectedMenuItem().set(ClosedSystemMenuOptions.VIEWREPORTS);
    }

    private void onAccounts(){
        Model.getInstance().getViewFactory().getClosedSystemSelectedMenuItem().set(ClosedSystemMenuOptions.ACCOUNTS);
    }

    private void onLogout(){
        // get stage
        Stage stage = (Stage) dashboard_btn.getScene().getWindow();
        // close the closed system window
        Model.getInstance().getViewFactory().closeStage(stage);
        // show login window
        Model.getInstance().getViewFactory().showLoginWindow();
        // set the login credentials flag to false to prevent logging in with bad credentials
        Model.getInstance().setClosedSystemLoginSuccessFlag(false);
    }
}
