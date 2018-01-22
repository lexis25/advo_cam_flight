package com.super_cargo.utils;

import com.super_cargo.utils.Flight.CommentsCompare;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public abstract class Board {

    public static List<Flight> getTimeInterval(int hourStart, int minuteStart, int dayStart,
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

        if (present.after(future)) {
            future.roll(future.MONTH, 1);
        }

        if (present.after(future) && future.get(future.MONTH) == 11) {
            future.roll(future.YEAR, 1);
        }

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

    public static List<Flight> getCouple(List<Flight> arrival, List<Flight> departing) {
        Collections.sort(arrival);
        List<Flight> listCouple = new ArrayList<Flight>();
        int result;
        for (int i = 0; i < departing.size(); i++) {

            if ((result = Collections.binarySearch(arrival, new Flight((getParseNumberFlight(departing.get(i).getNumberFlight(), true)),
                    departing.get(i).getDirectionFlight(), departing.get(i).getTimeFlight(), departing.get(i).getCommentsFlight()))) >= 0 &&
                    arrival.get(result).getTimeFlight().get(Calendar.DATE) == departing.get(i).getTimeFlight().get(Calendar.DATE)) {
                listCouple.add(new Flight(arrival.get(result).getNumberFlight() + " " + departing.get(i).getNumberFlight(),
                        arrival.get(result).getDirectionFlight(), arrival.get(result).getTimeFlight(), arrival.get(result).getCommentsFlight()));

            } else if ((result = Collections.binarySearch(arrival, new Flight(getParseNumberFlight(departing.get(i).getNumberFlight(), false),
                    departing.get(i).getDirectionFlight(), departing.get(i).getTimeFlight(), departing.get(i).getCommentsFlight()))) >= 0 &&
                    arrival.get(result).getTimeFlight().get(Calendar.DATE) == departing.get(i).getTimeFlight().get(Calendar.DATE)) {
                listCouple.add(new Flight(arrival.get(result).getNumberFlight() + " " + departing.get(i).getNumberFlight(),
                        arrival.get(result).getDirectionFlight(), arrival.get(result).getTimeFlight(), arrival.get(result).getCommentsFlight()));
            }
        }
        Collections.sort(listCouple, new Flight.TimeCompare());

        return listCouple;
    }

    public static List<Flight> getUnique(List<Flight> arrival, List<Flight> departing) {
        List<Flight> array = new ArrayList<Flight>();
        int coin = 0;
        for (int i = 0; i < arrival.size(); i++) {
            for (int j = 0; j < departing.size(); j++) {
                if (arrival.get(i).getTimeFlight().get(Calendar.DATE) == departing.get(j).getTimeFlight().get(Calendar.DATE)) {
                    if (getParseNumberFlight(arrival.get(i).getNumberFlight(), true).equals(departing.get(j).getNumberFlight())
                            || getParseNumberFlight(arrival.get(i).getNumberFlight(), false).equals(departing.get(j).getNumberFlight())) {
                        coin++;
                    }
                }
            }
            if (coin == 0) {
                array.add(arrival.get(i));
            }
            coin = 0;
        }
        Collections.sort(array, new Flight.TimeCompare());
        return array;
    }

    public static void removeFlight(List<Flight> schedule, String numberFlight) {
        Collections.sort(schedule);
        int result;
        if ((result = Collections.binarySearch(schedule, new Flight(numberFlight, "", "", ""))) >= 0) {
            schedule.remove(result);
        }
        Collections.sort(schedule, new Flight.TimeCompare());

    }

    private static String getParseNumberFlight(String number, boolean positive) {
        String[] array = number.split(" ");
        int result;
        String newString;
        if (array.length == 2) {
            if (positive) {
                result = Integer.parseInt(array[array.length - 1]) + 1;
            } else {
                result = Integer.parseInt(array[array.length - 1]) - 1;
            }

            if ((array[0] + " " + String.valueOf(result)).length() == number.length()) {
                newString = array[0] + " " + String.valueOf(result);
            } else {
                newString = array[0] + " 0" + String.valueOf(result);
            }
        } else {
            newString = "-1";
        }

        return newString;
    }

    public static void canceledFlights(List<Flight> schedule) {
        CommentsCompare compare = new CommentsCompare();
        Collections.sort(schedule, compare);
        int result;
        while ((result = Collections.binarySearch(schedule,
                new Flight("", "", "", "Отменен"), compare)) >= 0) {
            schedule.remove(result);
        }
        Collections.sort(schedule, new Flight.TimeCompare());
    }

}