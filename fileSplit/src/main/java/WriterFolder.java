import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class WriterFolder {
    private static final String PATH_FLASH_DRIVE = "h:/FOLDER/";

    public static void main(String[] args) {

        ParseSchedule tableDepartingToday = new ParseSchedule();
        ParseSchedule tableDepartingTomorrow = new ParseSchedule();

        ParseSchedule tableArrivalToday = new ParseSchedule();
        ParseSchedule tableArrivalTomorrow = new ParseSchedule();

        tableDepartingToday.setTable(new ParseSchedule().departing,new ParseSchedule().TODAY);
        List<Flight> departingToday = tableDepartingToday.getTable();

        tableArrivalToday.setTable(new ParseSchedule().arrival,new ParseSchedule().TODAY);
        List<Flight> arrivalToday = tableArrivalToday.getTable();

        tableDepartingTomorrow.setTable(new ParseSchedule().departing,new ParseSchedule().TOMORROW);
        List<Flight> departingTomorrow = tableDepartingTomorrow.getTable();

        tableArrivalTomorrow.setTable(new ParseSchedule().arrival,new ParseSchedule().TOMORROW);
        List<Flight> arrivalTomorrow = tableArrivalTomorrow.getTable();

        departingToday.addAll(departingTomorrow);
        arrivalToday.addAll(arrivalTomorrow);

        List<Flight> timeDeparting = Board.getTimeIntervalTable(7, 30, 24, 7, 30, 25, departingToday);
        List<Flight> timeArrival = Board.getTimeIntervalTable(7, 30, 24, 7, 30, 25, arrivalToday);

        Board.removeFlight(timeDeparting,"EY 8470");
        Board.removeFlight(timeArrival,"EY 8467");

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