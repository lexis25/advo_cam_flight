import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Calendar;

public class ParseSchedule {

    protected String departing = "table-departing";
    protected String arrival = "table-arrival";
    private String[] table;

    void setTable(String arrivalOrDeparting) {
        try {
            Document document = (Document) Jsoup.connect("http://hrk.aero/table/ajax_tablo_new.php?lang=ru&full=1&first=1").get();
            Element nameTable = document.getElementById(arrivalOrDeparting);
            Elements tagTable = nameTable.getElementsByTag("td");
            table = parseSchedule(tagTable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    String[] getTable() {
        return table;
    }

    private String[] parseSchedule(Elements tableElements) {
        Calendar nextDay = Calendar.getInstance();
        nextDay.add(nextDay.DATE, 1);

        String[] tableParsed = new String[tableElements.size()];

        for (int i = 0; i < tableElements.size(); i++) {
            tableParsed[i] = tableElements.get(i).text();

        }
        return tableParsed;
    }
}