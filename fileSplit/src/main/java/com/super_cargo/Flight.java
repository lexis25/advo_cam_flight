package com.super_cargo;

import java.util.Calendar;

public class Flight  implements Comparable<Flight>{
    private String numberFlight;
    private String directionFlight;
    private Calendar timeFlight = Calendar.getInstance();
    private String commentsFlight;

    Flight() {

    }

    public Flight(String numberFlight, String directionFlight, String timeFlight, String commentsFlight) {
        this.numberFlight = numberFlight;
        this.directionFlight = directionFlight;
        this.commentsFlight = commentsFlight;
        setTimeFlight(timeFlight);
    }

    public Flight(String numberFlight, String directionFlight, Calendar timeFlight, String commentsFlight) {
        this.numberFlight = numberFlight;
        this.directionFlight = directionFlight;
        this.timeFlight = timeFlight;
        this.commentsFlight = commentsFlight;
    }


    public String toString(){
        return numberFlight + "\n" +
                directionFlight + "\n"+
                timeFlight.getTime() + "\n" +
                commentsFlight;
    }

    public String getNumberFlight() {
        return numberFlight;
    }

    public String getDirectionFlight() {
        return directionFlight;
    }

    public Calendar getTimeFlight() {
        return timeFlight;
    }

    public String getCommentsFlight() {
        return commentsFlight;
    }

    public void setNumberFlight(String numberFlight) {
        this.numberFlight = numberFlight;
    }

    public void setDirectionFlight(String directionFlight) {
        this.directionFlight = directionFlight;
    }

    public void setTimeFlight(String timeFlight) {
        if (timeFlight.length() == 5) {
            this.timeFlight.set(this.timeFlight.HOUR_OF_DAY, Integer.parseInt(timeFlight.substring(0, 2)));
            this.timeFlight.set(this.timeFlight.MINUTE, Integer.parseInt(timeFlight.substring(3, 5)));
        } else if (timeFlight.length() == 16) {
            this.timeFlight.set(
                    Integer.parseInt(timeFlight.substring(12, 16)),
                    (Integer.parseInt(timeFlight.substring(9, 11)) - 1),
                    Integer.parseInt(timeFlight.substring(6, 8)),
                    Integer.parseInt(timeFlight.substring(0, 2)),
                    Integer.parseInt(timeFlight.substring(3, 5)));
        }
    }

    public void setCommentsFlight(String commentsFlight) {
        this.commentsFlight = commentsFlight;
    }

    public int compareTo(Flight o) {
        return numberFlight.compareTo(o.getNumberFlight());
    }
}