import java.util.Calendar;

public abstract class Board {


    private static String[] table;

    public static void main(String[] args) {
        String[] arrival = {
                "UH 3051", "Анталия", "11:15", "",
                "UH 251", "Стамбул", "12:40", "",
                "7W 4912", "Анталия", "16:00", "",
                "PS 8332", "Анталия", "16:20", "",
                "PS 025", "Киев (Борисполь)", "16:50", "",
                "LO 761", "Варшава", "17:20", "",
                "PC 426", "Стамбул", "17:40", "",
                "7W 4812", "Анталия", "18:10", "",
                "PS 023", "Киев (Борисполь)", "20:35", "",
                "B2 835", "Минск", "00:15 25-05-2017", "",
                "EY 8467", "Минск", "00:15 25-05-2017", ""
        };

        String[] departing ={
                "TK 1474","Стамбул","09:45","",
                "BAY 4325","Даламан","10:45","",
                "PS 026","Киев (Борисполь)","11:05","",
                "PS 7327","Анталия","12:05","",
                "UH 250","Стамбул","13:20","",
                "LO 760","Варшава","14:40","",
                "PC 427","Стамбул","18:40","",
                "PC 429","Стамбул","03:05 25-05-2017","",
                "B2 836","Минск","06:00 25-05-2017","",
                "EY 8470","Минск","06:00 25-05-2017","",
                "PS 024","Киев (Борисполь)","07:00 25-05-2017","",


        };
        //System.out.println(arrival[58]);
        //System.out.println("helloejufisefiejfisjefi");
       // readArray(setTimeIntervalTable(8, 30, 00, 00, 25, arrival));
       // System.out.println(arrival[0].substring(0,arrival[0].length()-2).equalsIgnoreCase(arrival[0].substring(0,arrival[0].length()-2)));
       String[] strings = setTimeIntervalTable(07,30,07,30,25,departing);
      // String[] wat = setCreateCoupleArray(arrival,departing);
       readArray(strings);
        //System.out.println(strings.length);
        //System.out.println(strings[0] + "|" + strings [1] + "|" + strings [2] + "|" + strings[3]);

    }

    static void readArray(String[] string) {
        for (int i = 0; i < string.length; i++) {
                System.out.print(string[i]);
                System.out.println();
        }
    }

    static String[] setTimeIntervalTable(int hourStart, int minuteStart, int hourFinish, int minuteFinish, int dayEnd, String[] flight) {

        Calendar present = Calendar.getInstance();
        present.set(present.HOUR_OF_DAY, hourStart);
        present.set(present.MINUTE, minuteStart);

        Calendar future = Calendar.getInstance();
        future.set(future.DATE, dayEnd);
        future.set(future.HOUR_OF_DAY, hourFinish);
        future.set(future.MINUTE, minuteFinish);

        int[] interval = new int[2];
        int counter = 0;

        for (int i = 0; i < flight.length; i += 4) {
            if (flight[i] != null) {
                if (flight[i + 2].length() == 5 &&
                        Integer.parseInt(flight[i + 2].substring(0, 2)) >= present.get(present.HOUR_OF_DAY) &&
                        Integer.parseInt(flight[i + 2].substring(3, 5)) >= present.get(present.MINUTE)) {
                    interval[0] = i;
                    break;
                }
            }
        }
        for (int j = flight.length - 1; j > 0; j -= 4) {
            if (flight[j] != null) {
                if (flight[j - 1].length() == 16 &&
                        Integer.parseInt(flight[j - 1].substring(0, 2)) <= future.get(future.HOUR_OF_DAY) &&
                        Integer.parseInt(flight[j - 1].substring(3, 5)) <= future.get(future.MINUTE) &&
                        Integer.parseInt(flight[j - 1].substring(6, 8)) == future.get(future.DATE)) {
                    interval[1] = j;
                    break;
                } else if (flight[j - 1].length() < 16) {
                    interval[1] = j;
                    break;
                }
            }
        }
        String[] clean = new String[interval[1] - interval[0]];
        for (int k = interval[0]; k < interval[1]; k++) {
            clean[counter] = flight[k];
            counter++;
        }
        return clean;
    }

    static String[] getTable() {
        return table;
    }

    static String[] setCreateCoupleArray(String[] arrival, String[] departing) {// trouble with last element
        String[] couple = new String[departing.length];
        int counter = 0;
        int coin = 0;
        for (int i = 0; i < arrival.length; i += 4) {
            for (int j = 0; j < departing.length; j += 4) {
                if (arrival[i] != null && departing[j] != null) {
                    if (arrival[i + 2].length() == departing[j + 2].length()) {
                        if ((Integer.parseInt(arrival[i].substring(arrival[i].length() - 3)) + 1)
                                == Integer.parseInt(departing[j].substring(departing[j].length() - 3))
                                || (Integer.parseInt(arrival[i].substring(arrival[i].length() - 3)) - 1)
                                == Integer.parseInt(departing[j].substring(departing[j].length() - 3))
                                || arrival[i].substring(0,arrival[i].length()-2).equalsIgnoreCase(departing[j].substring(0,departing[j].length()-2))) {
                            couple[counter] = "3" + arrival[i + 1] + " " + arrival[i] + "-" + departing[j];
                            counter++;
                            couple[counter] = arrival[i + 2];
                            counter++;
                            coin++;
                        }
                    }
                }
            }
            if (coin == 0 && departing[i] != null && arrival[i] != null) {
                couple[counter] = "3" + arrival[i + 1] + " " + arrival[i];
                counter++;
                couple[counter] = arrival[i + 2];
                counter++;
                couple[counter] = "3" + departing[i + 1] + " " + departing[i];
                counter++;
                couple[counter] = departing[i + 2];
                counter++;
            }
            coin = 0;
        }
        return couple;
    }

}