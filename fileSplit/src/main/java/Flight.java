import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Flight {
    private String numberFlight;
    private String directionFlight;
    private Calendar timeFlight = Calendar.getInstance();
    private String commentsFlight;

    Flight(String numberF, String directionF, String timeF, String commentsF) {
        this.numberFlight = numberF;
        this.directionFlight = directionF;
        this.commentsFlight = commentsF;
        setTimeFlight(timeF);

    }

    Flight() {

    }

    public Flight(String numberF, String directionF, Calendar timeF, String commentsF) {
        this.numberFlight = numberF;
        this.directionFlight = directionF;
        this.timeFlight = timeF;
        this.commentsFlight = commentsF;
    }

    public String toString(){
        return numberFlight + " " +
                directionFlight + " "+
                timeFlight.getTime() + " " +
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

}
