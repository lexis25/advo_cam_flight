package com.super_cargo.net;

import com.super_cargo.Flight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ParseSchedule {

    public final String departing = "table-departing";
    public final String arrival = "table-arrival";

    private static final String CLASS_TD_HTML = "flight-item flight-";

    public static final String YESTERDAY = "yesterday";
    public static final String TODAY = "today";
    public static final String TOMORROW = "tomorrow";

    private List<Flight> table = new ArrayList<Flight>();

    public void setTable(String arrivalOrDeparting, String classTag) {
        try {
            System.setProperty("javax.net.ssl.trustStore", "cer/11.jks");
            Document document = (Document) Jsoup.connect("https://hrk.aero/table/ajax_tablo_new.php?lang=ru&full=1&first=1").get();
            Element nameTable = document.getElementById(arrivalOrDeparting);
            Elements tagTable = nameTable.getElementsByClass(CLASS_TD_HTML + classTag);

            parseSchedule(tagTable,classTag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public List<Flight> getTable() {
        return table;
    }

    private void parseSchedule(Elements tableElements, String classTag) {
        Calendar calendar = Calendar.getInstance();
        String date = "";
        if(classTag.equalsIgnoreCase(YESTERDAY)){
            calendar.roll(calendar.DATE,-1);
            date = String.format(" %02d.%02d.%02d", calendar.get(calendar.DATE)
                    , calendar.get(calendar.MONTH) + 1, calendar.get(calendar.YEAR));

        }else if(classTag.equalsIgnoreCase(TODAY)){
            date = String.format(" %02d.%02d.%02d", calendar.get(calendar.DATE)
                    , calendar.get(calendar.MONTH) + 1, calendar.get(calendar.YEAR));

        }else if(classTag.equalsIgnoreCase(TOMORROW)){
            calendar.add(calendar.DATE, 1);
            date = String.format(" %02d.%02d.%02d", calendar.get(calendar.DATE)
                    , calendar.get(calendar.MONTH) + 1, calendar.get(calendar.YEAR));
        }

        for (int i = 0; i < tableElements.size(); i++) {
            table.add(new Flight(
                    tableElements.get(i).child(0).text(),
                    tableElements.get(i).child(1).text(),
                    tableElements.get(i).child(2).text() + date,
                    tableElements.get(i).child(3).text()
            ));
        }
    }
}