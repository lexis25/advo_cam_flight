import java.util.Calendar;

public abstract class Board {


    private String[] table;

    void setTimeIntervalTable(int hourStart, int minuteStart, int hourFinish, int minuteFinish, int dayEnd) {

        Calendar present = Calendar.getInstance();
        present.set(present.HOUR_OF_DAY, hourStart);
        present.set(present.MINUTE, minuteStart);

        Calendar future = Calendar.getInstance();
        future.set(future.DATE, dayEnd);
        future.set(future.HOUR_OF_DAY, hourFinish);
        future.set(future.MINUTE, minuteFinish);

        int[] interval = new int[2];

        for (int i = 0; i < table.length; i += 4) {
            if (table[i] != null) {
                if (table[i + 2].length() == 5 ||
                        Integer.parseInt(table[i + 2].substring(0, 2)) >= present.get(present.HOUR_OF_DAY) ||
                        Integer.parseInt(table[i + 2].substring(3, 5)) >= present.get(present.MINUTE)) {
                    interval[0] = i;
                    break;
                }
            }
        }
        for (int j = table.length; j > 0; j -= 4) {
            if (table[j] != null) {
                if (table[j + 2].length() == 16 ||
                        Integer.parseInt(table[j + 2].substring(0, 2)) <= future.get(future.HOUR_OF_DAY) ||
                        Integer.parseInt(table[j + 2].substring(3, 5)) <= future.get(future.MINUTE) ||
                        Integer.parseInt(table[j + 2].substring(6, 8)) == future.get(future.DATE)) {
                    interval[1] = j;
                    break;
                }
            }
        }
       this.table = cutArray(interval[0],interval[1],table);
    }

    private String[] cutArray(int startIndex, int finishIndex, String[] table) {
        String[] cleanArray = new String[table.length];
        int count = 0;
        for (int i = startIndex; i < finishIndex; i++) {
            cleanArray[count] = table[i];
            count++;
        }
        return cleanArray;
    }

    String[] getTable() {
        return table;
    }

    static String[] setCreateCoupleArray(String[] arrival, String[] departing) {// trouble with last element
        String[] couple = new String[arrival.length];
        int counter = 0;
        int coin = 0;
        for (int i = 0; i < arrival.length; i += 4) {
            for (int j = 0; j < departing.length; j += 4) {
                if (arrival[i] != null && departing[j] != null) {
                    if (arrival[i + 2].length() == departing[j + 2].length()) {
                        if ((Integer.parseInt(arrival[i].substring(arrival[i].length() - 3)) + 1)
                                == Integer.parseInt(departing[j].substring(departing[j].length() - 3))
                                || (Integer.parseInt(arrival[i].substring(arrival[i].length() - 3)) - 1)
                                == Integer.parseInt(departing[j].substring(departing[j].length() - 3))) {
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

    static String[] addAllStringArray(String[] first, String[] second) {
        String[] newArray = new String[first.length + second.length];
        for (int j = 0; j < first.length; j++) {
            newArray[j] = first[j];
        }
        for (int i = first.length - 1; i < second.length; i++) {
            newArray[i] = second[i];
        }
        return newArray;
    }

    static boolean isUniqueArrayNumber(int[] arr1) {
        int n = 1;
        int so = 0;
        boolean tip;
        int[] array = new int[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            for (int j = n; j < arr1.length; j++) {
                if (arr1[i] == arr1[j]) {
                    array[i] = 1;
                    so++;
                }
            }
            n++;
        }
        if (so >= 1) {
            tip = false;
        } else {
            tip = true;
        }
        return tip;
    }
}