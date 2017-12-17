package com.super_cargo.ui;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ControlPanel extends Application {
    private ToggleGroup groupAddress;
    private RadioButton def;
    private RadioButton newURL;
    private TextField textURL;
    private ComboBox comboBoxFrom;
    private ComboBox comboBoxBefore;
    private RadioButton txt;
    private RadioButton excel;
    private Button cancel;
    private Button start;
    private RadioButton saveDefPath;
    private RadioButton saveNewPath;



    public void start(Stage primaryStage) throws Exception {

        FlowPane pane = new FlowPane(Orientation.VERTICAL);
        pane.getChildren().add(createURLaddress());
        pane.getChildren().add(createDateBox());
        pane.getChildren().add(createFiles());
        pane.getChildren().add(createButtons());

        Scene scene = new Scene(pane, 400, 520);

        primaryStage.setTitle("Airport VRParser");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        Controller controller = new Controller();
        controller.setEvents();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private  VBox createURLaddress() {
        VBox addressVBox = new VBox();
        addressVBox.setPadding(new Insets(25));

        groupAddress = new ToggleGroup();
        def = new RadioButton("default");
        def.setPadding(new Insets(5));
        def.setSelected(true);
        def.setToggleGroup(groupAddress);
        def.setUserData("default");

        newURL = new RadioButton("new URL");
        newURL.setToggleGroup(groupAddress);
        newURL.setPadding(new Insets(5));
        newURL.setUserData("new URL");

        textURL = new TextField();
        textURL.setPromptText("www.address.com");
        textURL.setPadding(new Insets(5));

        addressVBox.getChildren().addAll(def, newURL, textURL);

        return addressVBox;

    }

    private  HBox createDateBox() {
        VBox dateFromVBox = new VBox();
        dateFromVBox.setPadding(new Insets(5));

        VBox dateBeforeVBox = new VBox();
        dateBeforeVBox.setPadding(new Insets(5));

        VBox labelBox = new VBox();
        labelBox.setPadding(new Insets(5));

        HBox dateContHBox = new HBox();
        dateContHBox.setPadding(new Insets(20));

        Label labelDate = new Label("Choose a date interval" + '\n' + " parse data");

        Label from = new Label("from");
        from.setPadding(new Insets(5));
        comboBoxFrom = new ComboBox();
        comboBoxFrom.getItems().addAll("yesterday", "today", "tomorrow");

        Label before = new Label("before");
        before.setPadding(new Insets(5));

        comboBoxBefore = new ComboBox();
        comboBoxBefore.getItems().addAll("yesterday", "today", "tomorrow");

        dateFromVBox.getChildren().addAll(from, comboBoxFrom);
        dateBeforeVBox.getChildren().addAll(before,comboBoxBefore);
        labelBox.getChildren().addAll(labelDate);

        dateContHBox.getChildren().addAll(labelBox,dateFromVBox, dateBeforeVBox);
        HBox dateHBox = new HBox();
        dateHBox.getChildren().addAll(dateContHBox);

        return dateHBox;
    }

    private VBox createFiles(){

        VBox filesVBox = new VBox();
        filesVBox.setPadding(new Insets(20));

        ToggleGroup groupFiles = new ToggleGroup();
        txt = new RadioButton("Create txt file schedule");
        txt.setSelected(true);
        txt.setPadding(new Insets(5));

        excel = new RadioButton("Create excel file schedule");
        excel.setPadding(new Insets(5));

        filesVBox.getChildren().addAll(txt,excel);

        return filesVBox;
    }

    private VBox createButtons(){
        HBox buttonHBox = new HBox(10);
        VBox buttonVBox = new VBox();

        buttonHBox.setPadding(new Insets(25));
        buttonVBox.setPadding(new Insets(25));

        cancel = new Button("cancel");
        start = new Button(" start ");

        Label labelSaveFile = new Label("set default path directory");

        ToggleGroup group = new ToggleGroup();
        saveDefPath = new RadioButton("default");
        saveDefPath.setPadding(new Insets(5));
        saveDefPath.setSelected(true);
        saveDefPath.setToggleGroup(group);

        saveNewPath = new RadioButton("new path");
        saveNewPath.setPadding(new Insets(5));
        saveNewPath.setToggleGroup(group);

        buttonHBox.getChildren().addAll(cancel,start);
        buttonVBox.getChildren().addAll(labelSaveFile,saveDefPath,saveNewPath,buttonHBox);

        return buttonVBox;
    }

    public class Controller {
        private String addressURL = "https://hrk.aero/table/ajax_tablo_new.php?lang=ru&full=1&first=1";
        private String path;

        private void setEvents(){
            groupAddress.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                    if(groupAddress.getSelectedToggle().getUserData().equals("default")){
                        addressURL = "https://hrk.aero/table/ajax_tablo_new.php?lang=ru&full=1&first=1";
                    }else{
                        addressURL = textURL.getText();
                    }
                }
            });
        }
    }
}