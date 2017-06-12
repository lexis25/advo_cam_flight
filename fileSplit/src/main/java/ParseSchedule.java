import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ParseSchedule {
    public static void main(String[] args) {
    }

    protected String departing = "table-departing";
    protected String arrival = "table-arrival";
    private List<Flight> table = new ArrayList<Flight>();

    void setTable(String arrivalOrDeparting) {
        try {
            Document document = (Document) Jsoup.connect("http://hrk.aero/table/ajax_tablo_new.php?lang=ru&full=1&first=1").get();
            Element nameTable = document.getElementById(arrivalOrDeparting);
            Elements tagTable = nameTable.getElementsByTag("td");
            parseSchedule(tagTable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    List<Flight> getTable() {
        return table;
    }

    private void parseSchedule(Elements tableElements) {
        for (int i = 0; i < tableElements.size(); i+=4) {
            this.table.add(new Flight(
                    tableElements.get(i).text(),
                    tableElements.get(i + 1).text(),
                    tableElements.get(i + 2).text(),
                    tableElements.get(i + 3).text()));

        }
    }
}