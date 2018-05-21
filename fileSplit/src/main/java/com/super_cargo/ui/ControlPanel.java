package com.super_cargo.ui;

import com.super_cargo.IO.FlightScheduleExcel;
import com.super_cargo.IO.WriterFolder;
import com.super_cargo.net.ParseSchedule;
import com.super_cargo.utils.Board;
import com.super_cargo.utils.Flight;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class ControlPanel extends Application {
    private ToggleGroup groupAddress;
    private RadioButton def;
    private RadioButton newURL;
    private TextField textURL;
    private ComboBox comboBoxFrom;
    private ComboBox comboBoxBefore;
    private ToggleGroup groupFiles;
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

    private VBox createURLaddress() {
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

    private HBox createDateBox() {
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
        dateBeforeVBox.getChildren().addAll(before, comboBoxBefore);
        labelBox.getChildren().addAll(labelDate);

        dateContHBox.getChildren().addAll(labelBox, dateFromVBox, dateBeforeVBox);
        HBox dateHBox = new HBox();
        dateHBox.getChildren().addAll(dateContHBox);

        return dateHBox;
    }

    private VBox createFiles() {

        VBox filesVBox = new VBox();
        filesVBox.setPadding(new Insets(20));

        groupFiles = new ToggleGroup();
        txt = new RadioButton("Create txt file schedule");
        txt.setSelected(true);
        txt.setPadding(new Insets(5));
        txt.setToggleGroup(groupFiles);
        txt.setUserData("Create txt file schedule");

        excel = new RadioButton("Create excel file schedule");
        excel.setPadding(new Insets(5));
        excel.setToggleGroup(groupFiles);
        excel.setUserData("Create excel file schedule");

        filesVBox.getChildren().addAll(txt, excel);

        return filesVBox;
    }

    private VBox createButtons() {
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

        buttonHBox.getChildren().addAll(cancel, start);
        buttonVBox.getChildren().addAll(labelSaveFile, saveDefPath, saveNewPath, buttonHBox);

        return buttonVBox;
    }

    public class Controller {
        private String addressURL;
        private String from;
        private String before;
        private boolean txtFile;
        private boolean excelFile;

        private void setEvents() {
            groupAddress.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                    if (groupAddress.getSelectedToggle().getUserData().equals("default")) {
                        addressURL = ParseSchedule.PATH;
                    } else {
                        addressURL = textURL.getText();
                    }
                }
            });

            comboBoxFrom.valueProperty().addListener(new ChangeListener() {

                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    from = newValue.toString();

                }
            });

            comboBoxBefore.valueProperty().addListener(new ChangeListener() {
                public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                    before = newValue.toString();
                }
            });

            groupFiles.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

                public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                    if (groupFiles.getSelectedToggle().getUserData().equals("Create txt file schedule")) {
                        txtFile = true;
                    } else if (groupFiles.getSelectedToggle().getUserData().equals("Create excel file schedule")) {
                        excelFile = true;
                    }
                }
            });

            start.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    if (from != null && before != null) {
                        ParseSchedule table = new ParseSchedule();
                        table.setTable();

                        List<Flight> departing = table.getTableDeparting();
                        List<Flight> arrival = table.getTableArrival();

                        Calendar past = Calendar.getInstance();
                        Calendar future = Calendar.getInstance();

                        int datePast = 0;
                        int dateFuture = 0;
                        if (from.equals("yesterday") && before.equals("today")) {
                            past.roll((past.DATE), -1);
                            datePast = past.get(past.DATE);
                            dateFuture = future.get(future.DATE);
                        } else if (from.equals("today") && before.equals("tomorrow")) {
                            datePast = past.get(past.DATE);
                            future.roll((future.DATE), 1);
                            dateFuture = future.get(future.DATE);
                        } else if (from.equals("yesterday") && before.equals("tomorrow")) {
                            past.roll((past.DATE), -1);
                            future.roll((future.DATE), 1);
                            datePast = past.get(past.DATE);
                            dateFuture = future.get(future.DATE);
                        }

                        List<Flight> timeDeparting = Board.getTimeInterval(7, 30, datePast, 7, 30, dateFuture, departing);
                        List<Flight> timeArrival = Board.getTimeInterval(7, 30, datePast, 7, 30, dateFuture, arrival);
                        Board.canceledFlights(timeDeparting);
                        Board.canceledFlights(timeArrival);
                        Board.removeFlight(timeDeparting, "EY 8470");
                        Board.removeFlight(timeArrival, "EY 8467");

                        if (txtFile) {
                            WriterFolder.createSchedule(timeArrival, "прилет.txt");
                            WriterFolder.createSchedule(timeDeparting, "вылет.txt");
                        }
                        if (excelFile) {
                            try {
                                new FlightScheduleExcel(timeArrival, timeDeparting);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        WriterFolder.createFolders(Board.getCouple(timeArrival, timeDeparting));
                        WriterFolder.createFolders(Board.getUnique(timeArrival, timeDeparting));
                        WriterFolder.createFolders(Board.getUnique(timeDeparting, timeArrival));
                    }
                }
            });

            cancel.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    System.exit(0);
                }
            });
        }
    }
}