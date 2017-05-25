

public abstract class Board {

    static String[] createCoupleArray(String[] arrival, String[] departing) {// trouble with last element
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