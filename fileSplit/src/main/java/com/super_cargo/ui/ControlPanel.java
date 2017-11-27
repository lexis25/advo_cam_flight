package com.super_cargo.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ControlPanel extends Application {


    public void start(Stage primaryStage) throws Exception {

        FlowPane pane = new FlowPane(Orientation.VERTICAL);
        pane.getChildren().add(createURLaddress());
        pane.getChildren().add(createDateBox());
        pane.getChildren().add(createFiles());
        pane.getChildren().add(createButtons());

        Scene scene = new Scene(pane, 500, 450);

        primaryStage.setTitle("Airport VRParser");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private static HBox createURLaddress() {
        VBox addressVBox = new VBox();
        addressVBox.setPadding(new Insets(25));

        ToggleGroup groupAddress = new ToggleGroup();
        RadioButton def = new RadioButton();
        def.setText("default");
        def.setPadding(new Insets(5));
        def.setSelected(true);
        def.setToggleGroup(groupAddress);

        RadioButton newURL = new RadioButton();
        newURL.setText("new URL");
        newURL.setToggleGroup(groupAddress);
        newURL.setPadding(new Insets(5));

        TextField textURL = new TextField();
        textURL.setPromptText("www.address.com");
        textURL.setPadding(new Insets(5));

        Line line = new Line();
        line.setStartX(138);
        line.setStartY(198);
        line.setEndX(500);
        line.setEndY(198);

        addressVBox.getChildren().addAll(def, newURL, textURL, line);

        Rectangle rectangleAddress = new Rectangle(89, 138, Paint.valueOf("#0c59cf"));

        HBox addressHBox = new HBox();
        addressHBox.getChildren().addAll(rectangleAddress, addressVBox);

        return addressHBox;

    }

    private static HBox createDateBox() {
        VBox dateFromVBox = new VBox();
        dateFromVBox.setPadding(new Insets(25));
        VBox dateBeforeVBox = new VBox();
        dateBeforeVBox.setPadding(new Insets(25));

        Label labelDate = new Label("Choose a date interval");

        Label from = new Label("from");
        from.setPadding(new Insets(5));
        ComboBox comboBoxFrom = new ComboBox();
        comboBoxFrom.getItems().addAll("yesterday", "today", "tomorrow");

        Label before = new Label("before");
        before.setPadding(new Insets(5));

        ComboBox comboBoxBefore = new ComboBox();
        comboBoxBefore.getItems().addAll("yesterday", "today", "tomorrow");

        Rectangle rectangleDate = new Rectangle(89, 108, Paint.valueOf("#3d8af7"));

        dateFromVBox.getChildren().addAll(labelDate, from, before, comboBoxFrom);
        dateBeforeVBox.getChildren().addAll(before, comboBoxBefore);
        HBox dateHBox = new HBox();
        dateHBox.getChildren().addAll(rectangleDate, dateFromVBox, dateBeforeVBox);

        return dateHBox;
    }

    private static HBox createFiles(){

        VBox filesVBox = new VBox();
        filesVBox.setPadding(new Insets(15));

        ToggleGroup groupFiles = new ToggleGroup();
        RadioButton txt = new RadioButton();
        txt.setText("Create txt file schedule");
        txt.setSelected(true);
        txt.setPadding(new Insets(5));

        RadioButton excel = new RadioButton();
        excel.setText("Create excel file schedule");
        excel.setPadding(new Insets(5));

        Rectangle rectangleFiles = new Rectangle(89,78, Paint.valueOf("#75a9f9"));

        filesVBox.getChildren().addAll(txt,excel);
        HBox filesHBox = new HBox();
        filesHBox.getChildren().addAll(rectangleFiles,filesVBox);

        return filesHBox;
    }

    private static HBox createButtons(){
        HBox buttonHBox = new HBox(10);
        buttonHBox.setPadding(new Insets(25));

        Button cancel = new Button("cancel");

        Button start = new Button(" start ");

        buttonHBox.getChildren().addAll(cancel,start);

        Rectangle rectangleButtons = new Rectangle(89,48,Paint.valueOf("#a8c6fa"));

        HBox buttonsContainer = new HBox(0);
        buttonsContainer.getChildren().addAll(rectangleButtons,buttonHBox);
        return buttonsContainer;
    }

}