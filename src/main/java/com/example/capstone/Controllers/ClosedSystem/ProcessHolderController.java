package com.example.capstone.Controllers.ClosedSystem;

import com.example.capstone.Models.Logic.Process;
import com.example.capstone.Models.Logic.State;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

public class ProcessHolderController implements Initializable {
    @FXML
    public TextField S2U;
    @FXML
    public TextField S2S;
    @FXML
    public TextField S2H;
    @FXML
    public TextField S1S;

    private ProcessHolderController processHolderController;

    @FXML
    public TextField S1H;
    @FXML
    public TextField S1U;
    @FXML
    public TextField S1pressure;
    @FXML
    public TextField S1Volume;
    @FXML
    public TextField S1temperature;
    @FXML
    public ChoiceBox ProcessPicker;
    @FXML
    public TextField NValue;
    @FXML
    public TextField S2pressure;
    @FXML
    public TextField S2volume;
    @FXML
    public TextField S2temperature;
    @FXML
    public HBox ProcessContainer;
    @FXML
    public VBox State1Container;
    @FXML
    public VBox ProcessConfigurations;
    @FXML
    public VBox State2Container;
    public Text firstStateLabel;
    public Text secondStateLabel;

    private BorderPane accountsView;
    public int c = 1;
    public int ind = 0;
    ObservableList<Character> processTypes = FXCollections.observableArrayList('p','v','t','x', 'y');

    private char processType;
    public void injectIntoMainController(AccountsController mainController) {
        mainController.setProcessHolderController(this);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ProcessContainer.setStyle("-fx-background-color: white;"+"-fx-border-style: solid inside;"+"-fx-border-color: green;");
        State1Container.setStyle("-fx-background-color: white");
        ProcessConfigurations.setStyle("-fx-background-color: white");
        State2Container.setStyle("-fx-background-color: white");

        ProcessPicker.setItems(processTypes);

        ProcessPicker.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newChoiceIndex) {
                processType = processTypes.get(newChoiceIndex.intValue());
            }
        });


    }
    public void setProcessHolderController(ProcessHolderController processHolderController) {
        this.processHolderController = processHolderController;
    }


    public void setData(Process process){

        State state1 = process.getState1();
        State state2 = process.getState2();

        S1pressure.setText("" + state1.getPressure());
        S1pressure.requestLayout();
        S1Volume.setText("" + state1.getVolume());
        S1Volume.requestLayout();
        S1temperature.setText("" + state1.getTemp());
        S1temperature.requestLayout();

        S2pressure.setText("" + state2.getPressure());
        S2pressure.requestLayout();
        S2volume.setText("" + state2.getVolume());
        S2volume.requestLayout();
        S2temperature.setText("" + state2.getTemp());
        S2temperature.requestLayout();
//        State state1 = process.getState1();
//        State state2 = process.getState2();
////        firstStateLabel.setText(state1.getStateName());
////        secondStateLabel.setText(state2.getStateName());
//
//
//
////        S2U.setText("");
////        S2S.setText("");
////        S2H.setText("");
////        S1H.setText("");
////        S1S.setText("");
////        S1U.setText("");
//        S1pressure.setText(""+state1.getPressure());
//        S1Volume.setText(""+state1.getVolume());
//        S1temperature.setText(""+state1.getTemp());
//        S2pressure.setText(""+state2.getPressure());
//        S2volume.setText(""+state2.getVolume());
//        S2temperature.setText(""+state2.getTemp());
//
////        Map<String, List<Double>> solvedStatesValues= process.getSolvedStatesValues();
////        List<State> statesSolved = process.solveProcess();
////        System.out.println(statesSolved);
////        System.out.println(statesSolved.get(0).getPressure());
////        System.out.println(statesSolved.get(1).getPressure());
//
//



    }

    public void setBlank(int maxVal){

        S2U.setText("");
        S2S.setText("");
        S2H.setText("");
        S1H.setText("");
        S1S.setText("");
        S1U.setText("");
        S1pressure.setText("");
        S1Volume.setText("");
        S1temperature.setText("");
        S2pressure.setText("");
        S2volume.setText("");
        S2temperature.setText("");

//        String processTypes[] = {"Temperature", "Pressure", "Volume"};
//        ProcessPicker.setItems(FXCollections.observableArrayList(processTypes));



//        int ind2 = ind+1;
//        firstStateLabel.setText("State "+ind);
//        secondStateLabel.setText("State "+ind2);
//        if(c % 2 == 0){
//            ind++;
//        }
//        c++;



    }

    public Map<String, List<Double>> getData(){
        // the indexing of each of those list of doubles will follow same order as UI, that is to say item @ index 0 would be pressure, last index is U
        Map<String, List<Double>> stateData = new HashMap<>();
        List<Double> state1Data = new ArrayList<>();
        List<Double> state2Data = new ArrayList<>();
//
//        double S2uIn = makeDouble(S2U);
//        double S2sIn = makeDouble(S2S);
//        double S2hIn = makeDouble(S2H);
//        double S1hIn = makeDouble(S1H);
//        double S1sIn = makeDouble(S1S);
//        double S1uIn = makeDouble(S1U);
        double S1pressureIn = makeDouble(S1pressure);
        //System.out.println(S1pressureIn);
        double S1volumeIn = makeDouble(S1Volume);
        double S1temperatureIn = makeDouble(S1temperature);
        double S2pressureIn = makeDouble(S2pressure);
        double S2volumeIn = makeDouble(S2volume);
        double S2temperatureIn = makeDouble(S2temperature);

        state1Data.add(S1pressureIn);
        state1Data.add(S1volumeIn);
        state1Data.add(S1temperatureIn);
//        state1Data.add(S1sIn);
//        state1Data.add(S1hIn);
//        state1Data.add(S1uIn);

        state2Data.add(S2pressureIn);
        state2Data.add(S2volumeIn);
        state2Data.add(S2temperatureIn);
//        state2Data.add(S2sIn);
//        state2Data.add(S2hIn);
//        state2Data.add(S2uIn);


        stateData.put("StateLeft", state1Data);
        stateData.put("StateRight", state2Data);

        return stateData;
    }

    public void setAccountsView(BorderPane accountsView) {
        this.accountsView = accountsView;
    }

    public BorderPane getAccountsView() {
        return accountsView;
    }

    private double makeDouble(TextField input){
        try {
            double inputValue = Double.parseDouble(input.getText());
            return inputValue;

        } catch (NumberFormatException e) {
//            System.out.println("Error not a double number");
            return -1;
        }
    }

    public char getProcessType() {
        return processType;
    }

    public void setFirstStateLabel(String firstStateLabel) {
        this.firstStateLabel.setText(firstStateLabel);
    }



    public String getFirstStateLabelString() {
        return firstStateLabel.getText();
    }

    public void setSecondStateLabel(String secondStateLabel) {
        this.secondStateLabel.setText(secondStateLabel);
    }

    public String getSecondStateLabelString() {
        return secondStateLabel.getText();
    }

    public void setS1pressure(TextField s1pressure) {
        S1pressure = s1pressure;
    }

    public void setS1temperature(TextField s1temperature) {
        S1temperature = s1temperature;
    }

    public void setS1Volume(TextField s1Volume) {
        S1Volume = s1Volume;
    }

    public void setS2pressure(TextField s2pressure) {
        S2pressure = s2pressure;
    }

    public void setS2temperature(TextField s2temperature) {
        S2temperature = s2temperature;
    }

    public void setS2volume(TextField s2volume) {
        S2volume = s2volume;
    }

    public TextField getS1pressure() {
        return S1pressure;
    }

    public TextField getS1temperature() {
        return S1temperature;
    }

    public TextField getS1Volume() {
        return S1Volume;
    }

    public TextField getS2pressure() {
        return S2pressure;
    }

    public TextField getS2temperature() {
        return S2temperature;
    }

    public TextField getS2volume() {
        return S2volume;
    }

    public VBox getState1Container() {
        return State1Container;
    }

    public VBox getState2Container() {
        return State2Container;
    }

    public VBox getProcessConfigurations() {
        return ProcessConfigurations;
    }

    public List<TextField> getStateLeftTextFields(){
        List<TextField> leftTextFields = new ArrayList<>();
        leftTextFields.add(S1pressure);
        leftTextFields.add(S1temperature);
        leftTextFields.add(S1Volume);
        return leftTextFields;
    }

    public List<TextField> getStateRightTextFields(){
        List<TextField> rightTextFields = new ArrayList<>();
        rightTextFields.add(S2pressure);
        rightTextFields.add(S2temperature);
        rightTextFields.add(S2volume);
        return rightTextFields;
    }
}
