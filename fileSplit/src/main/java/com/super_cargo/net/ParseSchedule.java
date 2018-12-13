package com.super_cargo.net;

import com.super_cargo.utils.Flight;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ParseSchedule {

    private static final String CLASS_TD_HTML = "flight-item flight-";
    public static final String DEPARTING_ID_TAG = "table-departing";
    public static final String ARRIVAL_ID_TAG = "table-arrival";
    public static final String PATH = "https://hrk.aero/table/ajax_tablo_new.php?lang=ru&full=1&first=1";

    private final String[] DAY_ID_TAG = {"yesterday", "today", "tomorrow"};
    private HashMap<String, Calendar> day = new HashMap<String, Calendar>();

    private List<Flight> tableArrival = new ArrayList<Flight>();
    private List<Flight> tableDeparting = new ArrayList<Flight>();

    public ParseSchedule() {
        setInitialDay();
    }

    private void setInitialDay() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.roll(Calendar.DATE, -1);
        Calendar today = Calendar.getInstance();
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.roll(Calendar.DATE, 1);
        day.put(DAY_ID_TAG[0], yesterday);
        day.put(DAY_ID_TAG[1], today);
        day.put(DAY_ID_TAG[2], tomorrow);
    }


    public void setTable() {
        try {
            System.setProperty("javax.net.ssl.trustStore", "cer/11.jks");
            Document document = (Document) Jsoup.connect(ParseSchedule.PATH).get();
            Element arrivalTable = document.getElementById(ARRIVAL_ID_TAG);
            Element departingTable = document.getElementById(DEPARTING_ID_TAG);
            for (int i = 0; i < DAY_ID_TAG.length; i++) {
                Elements tagArrivalTable = arrivalTable.getElementsByClass(CLASS_TD_HTML + DAY_ID_TAG[i]);
                Elements tagDepartingTable = departingTable.getElementsByClass(CLASS_TD_HTML + DAY_ID_TAG[i]);
                getParse(tagArrivalTable, DAY_ID_TAG[i], tableArrival);
                getParse(tagDepartingTable, DAY_ID_TAG[i], tableDeparting);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getParse(Elements tableElements, String classTag, List<Flight> array) {
        String date = "";
        if (day.containsKey(classTag)) {
            date = String.format(" %02d.%02d.%02d", day.get(classTag).get(Calendar.DATE),
                    day.get(classTag).get(Calendar.MONTH) + 1, day.get(classTag).get(Calendar.YEAR));
        }

        for (int i = 0; i < tableElements.size(); i++) {
            array.add(new Flight(
                    tableElements.get(i).child(0).text(),
                    tableElements.get(i).child(1).text(),
                    tableElements.get(i).child(2).text() + date,
                    tableElements.get(i).child(3).text()
            ));
        }
    }

    public List<Flight> getTableArrival() {
        return tableArrival;
    }

    public List<Flight> getTableDeparting() {
        return tableDeparting;
    }
}