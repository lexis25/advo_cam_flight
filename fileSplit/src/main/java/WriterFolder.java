import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class WriterFolder {
    private static final String PATH_FLASH_DRIVE = "h:/FOLDER/";

    public static void main(String[] args) {

        ParseSchedule tableDeparting = new ParseSchedule();
        ParseSchedule tableArrival = new ParseSchedule();

        tableDeparting.setTable(new ParseSchedule().departing);
        List<Flight> departing = tableDeparting.getTable();

        tableArrival.setTable(new ParseSchedule().arrival);
        List<Flight> arrival = tableArrival.getTable();

        List<Flight> timeArrival = Board.getTimeIntervalTable(7, 30, 12, 7, 30, 14, arrival);
        List<Flight> timeDeparting = Board.getTimeIntervalTable(7, 30, 12, 7, 30, 14, departing);

        Board.removeFlight(timeArrival,"EY 8467");
        Board.removeFlight(timeDeparting,"EY 8470");

        createSchedule(timeArrival,"прилет.txt");
        createSchedule(timeDeparting,"вылет.txt");

        List<Flight> couple = Board.getCouple(timeArrival,timeDeparting);
        List<Flight> firstUnique = Board.getUnique(timeArrival,timeDeparting);
        List<Flight> secondUnique = Board.getUnique(timeDeparting,timeArrival);
        createFolders(couple);
        createFolders(firstUnique);
        createFolders(secondUnique);

    }

    public static void createFolders(List<Flight> schedule) {
        Calendar nextDay = Calendar.getInstance();
        nextDay.add(nextDay.DATE, 1);
        String formatDate = String.format("%02d.%02d.%02d", nextDay.get(nextDay.DATE)
                , nextDay.get(nextDay.MONTH) + 1, nextDay.get(nextDay.YEAR));
        new File(PATH_FLASH_DRIVE + formatDate).mkdir();

        for (int i = 0; i < schedule.size(); i++) {
                if (schedule.get(i).getTimeFlight().get(Calendar.DATE) == nextDay.get(nextDay.DATE)) {
                        new File(PATH_FLASH_DRIVE  + formatDate + "/" + "3" + schedule.get(i).getDirectionFlight() +
                                " " + schedule.get(i).getNumberFlight()).mkdir();
                } else {
                    new File(PATH_FLASH_DRIVE +
                            "3" + schedule.get(i).getDirectionFlight() +
                            " " + schedule.get(i).getNumberFlight()).mkdir();
                }
        }
    }

    public static void createSchedule(List<Flight> schedule, String fileName) {
        try {
            FileWriter writer = new FileWriter(PATH_FLASH_DRIVE + "/" + fileName);
            for (int i = 0; i < schedule.size(); i++) {
                    writer.write(" | " + schedule.get(i).getNumberFlight() +
                            " | " + schedule.get(i).getDirectionFlight()  +
                            " | " + schedule.get(i).getTimeFlight().getTime());
                    writer.write("\r" + "\n");
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}