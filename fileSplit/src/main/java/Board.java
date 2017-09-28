import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class Board {


    static List<Flight> getTimeIntervalTable(int hourStart, int minuteStart, int dayStart,
                                             int hourFinish, int minuteFinish, int dayEnd,
                                             List<Flight> flight) {

        Calendar present = Calendar.getInstance();
        Calendar future = Calendar.getInstance();

        present.set(present.HOUR_OF_DAY, hourStart);
        present.set(present.MINUTE, minuteStart);
        present.set(present.DATE, dayStart);

        future.set(future.HOUR_OF_DAY, hourFinish);
        future.set(future.MINUTE, minuteFinish);
        future.set(future.DATE, dayEnd);

        int[] interval = new int[2];

        for (int i = 0; i < flight.size(); i++) {
            if (flight.get(i).getTimeFlight().after(present)) {
                interval[0] = i;
                break;
            }

        }
        for (int j = flight.size() - 1; j > 0; j--) {
            if (flight.get(j).getTimeFlight().before(future)) {
                interval[1] = j + 1;
                break;
            }
        }

        return flight.subList(interval[0], interval[1]);
    }

    static List<Flight> getCouple(List<Flight> arrival, List<Flight> departing) {
        List<Flight> array = new ArrayList<Flight>();
        for (int i = 0; i < arrival.size(); i++) {
            for (int j = 0; j < departing.size(); j++) {
                if (arrival.get(i).getTimeFlight().get(arrival.get(i).getTimeFlight().DATE) ==
                        departing.get(j).getTimeFlight().get(departing.get(j).getTimeFlight().DATE)) {
                    if (getParseNumberFlight(arrival.get(i).getNumberFlight()) + 1 == getParseNumberFlight(departing.get(j).getNumberFlight())
                            || getParseNumberFlight(arrival.get(i).getNumberFlight()) - 1 == getParseNumberFlight(departing.get(j).getNumberFlight())) {
                        array.add(new Flight(arrival.get(i).getNumberFlight() +
                                " " + departing.get(j).getNumberFlight(), arrival.get(i).getDirectionFlight(),
                                arrival.get(i).getTimeFlight(), arrival.get(i).getCommentsFlight()));
                    }
                }
            }
        }
        return array;
    }

    static List<Flight> getUnique(List<Flight> arrival, List<Flight> departing) {
        List<Flight> array = new ArrayList<Flight>();
        int coin = 0;
        for (int i = 0; i < arrival.size(); i++) {
            for (int j = 0; j < departing.size(); j++) {
                if (arrival.get(i).getTimeFlight().get(arrival.get(i).getTimeFlight().DATE) ==
                        departing.get(j).getTimeFlight().get(departing.get(j).getTimeFlight().DATE)) {
                    if (getParseNumberFlight(arrival.get(i).getNumberFlight()) + 1 == getParseNumberFlight(departing.get(j).getNumberFlight())
                            || getParseNumberFlight(arrival.get(i).getNumberFlight()) - 1 == getParseNumberFlight(departing.get(j).getNumberFlight())) {
                        coin++;
                    }

                }

            }
            if (coin == 0) {
                array.add(arrival.get(i));
            }
            coin = 0;
        }
        return array;
    }

    static void removeFlight(List<Flight> schedule, String numberFlight) {
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).getNumberFlight().equalsIgnoreCase(numberFlight)) {
                schedule.remove(i);
            }
        }
    }

    private static int getParseNumberFlight(String number) {
        String[] array = number.split(" ");
        return Integer.parseInt(array[array.length - 1]);
    }
}