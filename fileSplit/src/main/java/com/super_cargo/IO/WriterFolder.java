package com.super_cargo.IO;

import com.super_cargo.utils.Flight;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class WriterFolder {
    private static final String PATH_FLASH_DRIVE = "h:/FOLDER/";
    private static final Calendar flag = Calendar.getInstance();

    public static void createFolders(List<Flight> schedule) {
        String past = null;
        String future = null;

        if (schedule.get(0).getTimeFlight().get(Calendar.DATE) < flag.get(Calendar.DATE)) {
            past = String.format("%02d.%02d.%02d", schedule.get(0).getTimeFlight().get(Calendar.DATE),
                    schedule.get(0).getTimeFlight().get(Calendar.MONTH) + 1,
                    schedule.get(0).getTimeFlight().get(Calendar.YEAR));
            new File(PATH_FLASH_DRIVE + past).mkdir();

        }

        if (schedule.get(schedule.size() - 1).getTimeFlight().get(Calendar.DATE) > flag.get(Calendar.DATE)) {
            future = String.format("%02d.%02d.%02d", schedule.get(schedule.size() - 1).getTimeFlight().get(Calendar.DATE),
                    schedule.get(schedule.size() - 1).getTimeFlight().get(Calendar.MONTH) + 1,
                    schedule.get(schedule.size() - 1).getTimeFlight().get(Calendar.YEAR));
            new File(PATH_FLASH_DRIVE + future).mkdir();
        }

        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).getTimeFlight().get(Calendar.DATE) < flag.get(Calendar.DATE)) {
                new File(PATH_FLASH_DRIVE + past + "/" + "3" + schedule.get(i).getDirectionFlight() +
                        " " + schedule.get(i).getNumberFlight()).mkdir();
            }
            if (schedule.get(i).getTimeFlight().get(Calendar.DATE) > flag.get(Calendar.DATE)) {
                new File(PATH_FLASH_DRIVE + future + "/" + "3" + schedule.get(i).getDirectionFlight() +
                        " " + schedule.get(i).getNumberFlight()).mkdir();
            }
            if (schedule.get(i).getTimeFlight().get(Calendar.DATE) == flag.get(Calendar.DATE)) {

                new File(PATH_FLASH_DRIVE + "3" + schedule.get(i).getDirectionFlight() +
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