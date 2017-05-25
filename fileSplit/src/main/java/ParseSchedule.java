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
        int count = 0;
        for (int i = 2; i < tableElements.size(); i += 4) {
            if (tableElements.get(i).text().length() == 5) {
                if (Integer.parseInt(tableElements.get(i).text().substring(0, 2) + tableElements.get(i).text().substring(3, 5)) >= 730) {
                    tableParsed[count] = tableElements.get(i - 2).text();
                    count++;
                    tableParsed[count] = tableElements.get(i - 1).text();
                    count++;
                    tableParsed[count] = tableElements.get(i).text();
                    count++;
                    tableParsed[count] = tableElements.get(i + 1).text();
                    count++;
                }
            }
            if (tableElements.get(i).text().length() == 16) {
                if (Integer.parseInt(tableElements.get(i).text().substring(6, 8)) == nextDay.get(nextDay.DATE)
                        & Integer.parseInt(tableElements.get(i).text().substring(0, 2) + tableElements.get(i).text().substring(3, 5)) <= 730) {
                    tableParsed[count] = tableElements.get(i - 2).text();
                    count++;
                    tableParsed[count] = tableElements.get(i - 1).text();
                    count++;
                    tableParsed[count] = tableElements.get(i).text();
                    count++;
                    tableParsed[count] = tableElements.get(i + 1).text();
                    count++;
                }
            }
        }
        return tableParsed;
    }

}