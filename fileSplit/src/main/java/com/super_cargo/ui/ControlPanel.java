package com.super_cargo.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ControlPanel extends Application {


    public void start(Stage primaryStage) throws Exception {

        Pane pane = new Pane();
        pane.getChildren().addAll(createURLaddress());

        Scene scene = new Scene(pane,500,400);

        primaryStage.setTitle("Airport VRParser");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    private static HBox createURLaddress(){
        VBox addressVBox = new VBox(10);
        addressVBox.setPadding(new Insets(25));

        ToggleGroup groupAddress = new ToggleGroup();
        RadioButton def = new RadioButton();
        def.setText("default");
        def.setSelected(true);
        def.setToggleGroup(groupAddress);

        RadioButton newURL = new RadioButton();
        newURL.setText("new URL");
        newURL.setToggleGroup(groupAddress);

        TextField textURL = new TextField();
        textURL.setPromptText("www.address.com");

        Line line = new Line();
        line.setStartX(138);
        line.setStartY(198);
        line.setEndX(500);
        line.setEndY(198);

        addressVBox.getChildren().addAll(def,newURL,textURL,line);

        Rectangle rectangleAddress = new Rectangle(89,138,Paint.valueOf("#0c59cf"));
        HBox addressHBox = new HBox();
        addressHBox.getChildren().addAll(rectangleAddress,addressVBox);

        return addressHBox;

    }

    private static HBox createDateBox(){
        VBox dateVBox = new VBox(10);
        dateVBox.setPadding(new Insets(25));

        Label labelDate = new Label("Choose a date interval");
        Label from = new Label("from");
        Label before = new Label("before");

        ComboBox comboBoxDate = new ComboBox();
        comboBoxDate.getItems().addAll("yesterday","today","tomorrow");




        Rectangle rectangleDate = new Rectangle(89,108,Paint.valueOf("#3d8af7"));

        return null;
    }

}