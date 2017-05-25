import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

public class WriterFolder {
    private static final String PATH_FLASH_DRIVE = "h:/FOLDER/";

    public static void main(String[] args) {

        ParseSchedule tableDeparting = new ParseSchedule();
        ParseSchedule tableArrival = new ParseSchedule();

        tableDeparting.setTable(new ParseSchedule().departing);
        tableArrival.setTable(new ParseSchedule().arrival);

        String[] departing = tableDeparting.getTable();
        String[] arrival = tableArrival.getTable();

        createSchedule(arrival, "прилет.txt");
        createSchedule(departing, "вылет.txt");

        createFolders(arrival);
        createFolders(departing);

    }

    public static void createFolders(String[] schedule) {
        Calendar nextDay = Calendar.getInstance();
        nextDay.add(nextDay.DATE, 1);
        String formatDate = String.format("%02d.%02d.%02d", nextDay.get(nextDay.DATE)
                , nextDay.get(nextDay.MONTH) + 1, nextDay.get(nextDay.YEAR));
        new File(PATH_FLASH_DRIVE + formatDate).mkdir();
        for (int i = 0; i < schedule.length; i += 4) {
            if (schedule[i] != null) {
                if (schedule[i + 2].length() == 16) {
                    if (Integer.parseInt(schedule[i + 2].substring(6, 8)) == nextDay.get(nextDay.DATE)) {
                        new File(PATH_FLASH_DRIVE + formatDate + "/" + "3" + schedule[i + 1] + " " + schedule[i]).mkdir();
                    }
                } else {
                    new File(PATH_FLASH_DRIVE + "3" + schedule[i + 1] + schedule[i]).mkdir();
                }
            }
        }
    }

    public static void createSchedule(String[] schedule, String fileName) {
        try {
            FileWriter writer = new FileWriter(PATH_FLASH_DRIVE + "/" + fileName);
            for (int i = 0; i < schedule.length; i += 4) {
                if (schedule[i] != null) {
                    writer.write(" | " + schedule[i] + "| " + schedule[i + 1] + "| " + schedule[i + 2] + "| " + schedule[i + 3]);
                    writer.write("\r" + "\n");
                }
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}