package com.super_cargo.ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ControlPanel extends Application {


    public void start(Stage primaryStage) throws Exception {
        Text text = new Text(10,20,"Parser Schedule Airport");
        text.setFont(new Font(40));

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        Button startButton = new Button("start");
        startButton.setPrefSize(100,20);
        Button cancelButton = new Button("cancel");
        startButton.setPrefSize(100,20);

        hbox.getChildren().addAll(startButton,cancelButton);

        BorderPane pane = new BorderPane();
        pane.setTop(text);
        pane.setBottom(hbox);


        Scene scene = new Scene(pane, 640, 480);
        primaryStage.setTitle("Parser Schedule Airport");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }


}