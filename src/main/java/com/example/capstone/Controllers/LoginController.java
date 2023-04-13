package com.example.capstone.Controllers;

import com.example.capstone.Models.Model;
import com.example.capstone.Views.SystemType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox<SystemType> system_selector; // this makes the choice box have options of the type enum class from same name
    public Label username_address_label;
    public TextField username_address_field;
    public TextField password_field;
    public Button login_btn;
    public Label error_lbl;
    public ImageView IconLoginImageView;


    // we want to make a singleton of the view factory so the entire app uses only one object
    // if someone clicks a button we want to be able to go to the view factory and call opon the correct
    // controllers and Fxml file
    @Override // so once this fxml is initialized, if you click the login button then you access the singleton view factory instantiation
    public void initialize(URL url, ResourceBundle resourceBundle) {

        IconLoginImageView.setImage(new Image(String.valueOf(getClass().getResource("/Images/Icon.png"))));
        system_selector.setItems(FXCollections.observableArrayList(SystemType.CLOSED_SYSTEM,SystemType.OPEN_SYSTEM)); // now we are actually setting the choice box with these options with an observable array list which is exactly what it sounds like
        system_selector.setValue(Model.getInstance().getViewFactory().getLoginSystemType()); // this sets the actual account type to whatever it has been set to in the view factory, and we
        //have it set to closed systems by default in the view factory constructor
        system_selector.valueProperty().addListener(observable -> Model.getInstance().getViewFactory().setLoginSystemType(system_selector.getValue())); // what this call does is it checks the valueProperty of the choice box
        // (what your selected value is I think) and it adds a listener which takes whatever in (what you selected) and then it is going to
        // set the observable (what choice you made) to whatever it's value is that is has been given

        login_btn.setOnAction(event -> onLogin()); // now we reference the onLogin method when the login button is clicked
    }


    private void onLogin(){ // we specified this method so that we can do things like close the login window once we have already logged in
        Stage stage = (Stage) error_lbl.getScene().getWindow(); // what we are doing because of this issue below, not having access to the stage
        // what we do is grab any id/controller, then get the scene it is in, then get it's current window, although this leaves us with a window type object
        // , this is why we cast to a type of Stage, because they are practically the same object a window and a stage, contains the same data so we just cast to solve our problem
         // this would all work out so simply if we could pass in the specific
        // stage that the login page is using, however it is not so simple to get the stage that is actually being used by the login window
        if(Model.getInstance().getViewFactory().getLoginSystemType() == SystemType.CLOSED_SYSTEM){
            // Evaluate Closed System Login Credentials
            Model.getInstance().evaluateClosedSystemCredentials(username_address_field.getText(), password_field.getText());
            if(Model.getInstance().getClosedSystemLoginSuccessFlag()){
                Model.getInstance().getViewFactory().showClosedSystemWindow();
                // close the login stage now
                Model.getInstance().getViewFactory().closeStage(stage);

            } else{
                username_address_field.setText("");
                password_field.setText("");
                error_lbl.setText("No Such Login Credentials");
            }
        }
        else{
//            Model.getInstance().getViewFactory().showOpenSystemWindow() this method does not even currently exist inside
            // of the view factory, this would just be what we would do if we were actually working on open systems
        }
    }
}
