package com.super_cargo.IO;

import com.super_cargo.Flight;
import com.super_cargo.net.ParseSchedule;
import com.super_cargo.utils.Board;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class WriterFolder {
    private static final String PATH_FLASH_DRIVE = "h:/FOLDER/";

    public static void main(String[] args) throws IOException {

        ParseSchedule tableDeparting = new ParseSchedule();
        ParseSchedule tableArrival = new ParseSchedule();

        tableDeparting.setTable(ParseSchedule.DEPARTING_ID_TAG);
        List<Flight> departing = tableDeparting.getTable();

        tableArrival.setTable(ParseSchedule.ARRIVAL_ID_TAG);
        List<Flight> arrival = tableArrival.getTable();

        List<Flight> timeDeparting = Board.getTimeIntervalTable(7, 30, 10, 7, 30, 11, departing);
        List<Flight> timeArrival = Board.getTimeIntervalTable(7, 30, 10, 7, 30, 11, arrival);
        
        Board.removeFlight(timeDeparting, "EY 8470");
        Board.removeFlight(timeArrival, "EY 8467");

        new FlightScheduleExcel(timeArrival, timeDeparting);

        createSchedule(timeArrival, "прилет.txt");
        createSchedule(timeDeparting, "вылет.txt");

        createFolders(Board.getCouple(timeArrival, timeDeparting));
        createFolders(Board.getUnique(timeArrival, timeDeparting));
        createFolders(Board.getUnique(timeDeparting, timeArrival));

    }

    public static void createFolders(List<Flight> schedule) {
        Calendar nextDay = Calendar.getInstance();
        nextDay.add(nextDay.DATE, 1);
        String formatDate = String.format("%02d.%02d.%02d", nextDay.get(nextDay.DATE)
                , nextDay.get(nextDay.MONTH) + 1, nextDay.get(nextDay.YEAR));
        new File(PATH_FLASH_DRIVE + formatDate).mkdir();

        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).getTimeFlight().get(Calendar.DATE) == nextDay.get(nextDay.DATE)) {
                new File(PATH_FLASH_DRIVE + formatDate + "/" + "3" + schedule.get(i).getDirectionFlight() +
                        " " + schedule.get(i).getNumberFlight()).mkdir();
            } else {
                new File(PATH_FLASH_DRIVE +
                        "3" + schedule.get(i).getDirectionFlight() +
                        " " + schedule.get(i).getNumberFlight()).mkdir();
            }
        }
    }

    public static void createSchedule(List<Flight> schedule, String fileName) {
        SimpleDateFormat pattern = new SimpleDateFormat("dd/ HH:mm");
        try {
            FileWriter writer = new FileWriter(PATH_FLASH_DRIVE + "/" + fileName);
            for (int i = 0; i < schedule.size(); i++) {
                writer.write(" | " + schedule.get(i).getNumberFlight() +
                        " | " + schedule.get(i).getDirectionFlight() +
                        " | " + pattern.format(schedule.get(i).getTimeFlight().getTime()));
                writer.write("\r" + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}