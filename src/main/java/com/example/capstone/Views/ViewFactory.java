package com.example.capstone.Views;

import com.example.capstone.Controllers.ClosedSystem.ClosedSystemController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

// combines the dashboard and the menu displayed side by side
public class ViewFactory {


    private SystemType loginSystemType; // how we are going to set which system type we are using

    // Closed System views
    private final ObjectProperty<ClosedSystemMenuOptions> closedSystemSelectedMenuItem;
    private AnchorPane dashboardView;
    private AnchorPane viewReportsView;
    private BorderPane accountsView;

    // open system views
    private final StringProperty openSystemSelectedMenuItem;
    private AnchorPane createClosedSystemUserView;

    public ViewFactory(){
        this.loginSystemType = SystemType.CLOSED_SYSTEM;
        this.closedSystemSelectedMenuItem = new SimpleObjectProperty<>();
        this.openSystemSelectedMenuItem = new SimpleStringProperty("");
    }

    public SystemType getLoginSystemType() {
        return loginSystemType;
    } // how we get and set which system type we are using

    public void setLoginSystemType(SystemType loginSystemType) {
        this.loginSystemType = loginSystemType;
    }

    public ObjectProperty<ClosedSystemMenuOptions> getClosedSystemSelectedMenuItem() {
        return closedSystemSelectedMenuItem;
    }

    public AnchorPane getDashboardView(){
        if(dashboardView == null){ // if we do not have the view then we load the correct fxml
            try{ // we first check is dashboard is equal to null, every time that the user goes from dashboard to somewhere else and comes back, we should be able to use this instantiation without loading it again to save computation
                // basically if the object has already been created then we use it, makes app faster
                dashboardView = new FXMLLoader(getClass().getResource("/Fxml/ClosedSystem/Dashboard.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return dashboardView;
    }

    public AnchorPane getViewReportsView(){ // opens the view reports fxml page in a pseudo singleton way
        if(viewReportsView == null){
            try{
                viewReportsView = new FXMLLoader(getClass().getResource("/Fxml/ClosedSystem/ViewReports.fxml")).load();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return  viewReportsView;
    }

    public BorderPane getAccountsView(){ // opens the view reports fxml page in a pseudo singleton way

        if(accountsView == null){
            try{
                accountsView = new FXMLLoader(getClass().getResource("/Fxml/ClosedSystem/Accounts.fxml")).load();

            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return  accountsView;
    }

    private void createStage(FXMLLoader loader){
        Scene scene = null;
        try{
            scene = new Scene(loader.load()); // we put this inside of the try catch in case the file that the loader is looking for does not exist
        } catch (Exception e){
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/Images/Icon.png")))); // returns observable list of images, how we add an icon as well to the top
        // border part of the application
        stage.setResizable(true); // LATER ON WE WILL NEED TO DELETE this and make all the css resizeable
        stage.setScene(scene);
        stage.setTitle("Capstone H.E.A.T.S.");
        stage.show();
    }

    public void showLoginWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Login.fxml"));
        createStage(loader); // we just call this function to load each individual fxml file when we need it
    }


    public void showClosedSystemWindow(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ClosedSystem/ClosedSystem.fxml"));

        ClosedSystemController closedSystemController = new ClosedSystemController();
        loader.setController(closedSystemController); // we had to set this manually because of removing the fx controller from the closed system fxml file
        createStage(loader);
    }

    public void closeStage(Stage stage){ // closes a stage (window) for us as a utility function
        stage.close();
    }
}
