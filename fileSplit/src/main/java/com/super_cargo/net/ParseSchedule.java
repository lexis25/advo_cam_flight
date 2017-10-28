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

    private static final String CLASS_TD_HTML = "flight-item flight-";

    public static final String DEPARTING_ID_TAG = "table-departing";
    public static final String ARRIVAL_ID_TAG = "table-arrival";

    private final String [] DAY_ID_TAG = {"yesterday","today","tomorrow"};

    private List<Flight> table = new ArrayList<Flight>();

    public void setTable(String arrivalOrDeparting) {
        try {
            System.setProperty("javax.net.ssl.trustStore", "cer/11.jks");
            Document document = (Document) Jsoup.connect("https://hrk.aero/table/ajax_tablo_new.php?lang=ru&full=1&first=1").get();
            Element nameTable = document.getElementById(arrivalOrDeparting);
            for (int i = 0; i < DAY_ID_TAG.length ; i++) {
                Elements tagTable = nameTable.getElementsByClass(CLASS_TD_HTML + DAY_ID_TAG[i]);
                parseSchedule(tagTable, DAY_ID_TAG [i]);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void parseSchedule(Elements tableElements, String classTag) {
        Calendar calendar = Calendar.getInstance();
        String date = "";
        if(classTag.equalsIgnoreCase(DAY_ID_TAG[0])){
            calendar.roll(calendar.DATE,-1);
            date = String.format(" %02d.%02d.%02d", calendar.get(calendar.DATE)
                    , calendar.get(calendar.MONTH) + 1, calendar.get(calendar.YEAR));

        }else if(classTag.equalsIgnoreCase(DAY_ID_TAG[1])){
            date = String.format(" %02d.%02d.%02d", calendar.get(calendar.DATE)
                    , calendar.get(calendar.MONTH) + 1, calendar.get(calendar.YEAR));

        }else if(classTag.equalsIgnoreCase(DAY_ID_TAG[2])){
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

    public List<Flight> getTable() {
        return table;
    }
}