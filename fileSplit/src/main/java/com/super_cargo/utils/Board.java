package com.super_cargo.utils;

import com.super_cargo.Flight;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public abstract class Board {


    public static List<Flight> getTimeIntervalTable(int hourStart, int minuteStart, int dayStart,
                                                    int hourFinish, int minuteFinish, int dayEnd,
                                                    List<Flight> flight) {
        canceledFlights(flight);

        Calendar present = Calendar.getInstance();
        Calendar future = Calendar.getInstance();

        present.set(present.HOUR_OF_DAY, hourStart);
        present.set(present.MINUTE, minuteStart);
        present.set(present.DATE, dayStart);

        future.set(future.HOUR_OF_DAY, hourFinish);
        future.set(future.MINUTE, minuteFinish);
        future.set(future.DATE, dayEnd);

        if(present.after(future)){
            future.roll(future.MONTH,1);
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
        List<Flight> array = new ArrayList<Flight>();
        for (int i = 0; i < arrival.size(); i++) {
            for (int j = 0; j < departing.size(); j++) {
                if (arrival.get(i).getTimeFlight().get(arrival.get(i).getTimeFlight().DATE) ==
                        departing.get(j).getTimeFlight().get(departing.get(j).getTimeFlight().DATE)) {
                    if (getParseNumberFlight(arrival.get(i).getNumberFlight()) + 1 == getParseNumberFlight(departing.get(j).getNumberFlight())
                            || getParseNumberFlight(arrival.get(i).getNumberFlight()) - 1 == getParseNumberFlight(departing.get(j).getNumberFlight())) {
                        array.add(new Flight(arrival.get(i).getNumberFlight() + " " + departing.get(j).getNumberFlight(),
                                arrival.get(i).getDirectionFlight(), arrival.get(i).getTimeFlight(), arrival.get(i).getCommentsFlight()));
                    }
                }
            }
        }
        return array;
    }

    public static List<Flight> getUnique(List<Flight> arrival, List<Flight> departing) {
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

    public static void removeFlight(List<Flight> schedule, String numberFlight) {
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).getNumberFlight().equalsIgnoreCase(numberFlight)) {
                schedule.remove(i);
                i--;
            }
        }
    }

    private static int getParseNumberFlight(String number) {
        String[] array = number.split(" ");
        return Integer.parseInt(array[array.length - 1]);
    }

    private static void canceledFlights(List<Flight> schedule) {
        for (int i = 0; i < schedule.size(); i++) {
            if(schedule.get(i).getCommentsFlight().equals("Отменен")){
                schedule.remove(i);
                i--;
            }
        }
    }
}