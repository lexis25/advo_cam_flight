package com.super_cargo.IO;

import com.super_cargo.utils.Flight;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class WriterFolder {

    private static String path_save_files = "h:/FOLDER/";
    private static final Calendar flag = Calendar.getInstance();

    public static void createFolders(List<Flight> schedule) {
        String past = null;
        String future = null;

        if (schedule.get(0).getTimeFlight().get(Calendar.DATE) < flag.get(Calendar.DATE)) {
            past = String.format("%02d.%02d.%02d", schedule.get(0).getTimeFlight().get(Calendar.DATE),
                    schedule.get(0).getTimeFlight().get(Calendar.MONTH) + 1,
                    schedule.get(0).getTimeFlight().get(Calendar.YEAR));
            new File(path_save_files + past).mkdir();

        }

        if (schedule.get(schedule.size() - 1).getTimeFlight().get(Calendar.DATE) > flag.get(Calendar.DATE)) {
            future = String.format("%02d.%02d.%02d", schedule.get(schedule.size() - 1).getTimeFlight().get(Calendar.DATE),
                    schedule.get(schedule.size() - 1).getTimeFlight().get(Calendar.MONTH) + 1,
                    schedule.get(schedule.size() - 1).getTimeFlight().get(Calendar.YEAR));
            new File(path_save_files + future).mkdir();
        }

        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).getTimeFlight().get(Calendar.DATE) < flag.get(Calendar.DATE)) {
                new File(path_save_files + past + "/" + "3" + schedule.get(i).getDirectionFlight() +
                        " " + schedule.get(i).getNumberFlight()).mkdir();
            }
            if (schedule.get(i).getTimeFlight().get(Calendar.DATE) > flag.get(Calendar.DATE)) {
                new File(path_save_files + future + "/" + "3" + schedule.get(i).getDirectionFlight() +
                        " " + schedule.get(i).getNumberFlight()).mkdir();
            }
            if (schedule.get(i).getTimeFlight().get(Calendar.DATE) == flag.get(Calendar.DATE)) {

                new File(path_save_files + "3" + schedule.get(i).getDirectionFlight() +
                        " " + schedule.get(i).getNumberFlight()).mkdir();
            }
        }
    }

    public static void createSchedule(List<Flight> schedule, String fileName) {
        SimpleDateFormat pattern = new SimpleDateFormat("dd/ HH:mm");
        try {
            FileWriter writer = new FileWriter(path_save_files + "/" + fileName);
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

    public static String getPath() {
        return path_save_files;
    }

    public static void setPaths(String path_save_files) {
        WriterFolder.path_save_files = path_save_files;
    }
}