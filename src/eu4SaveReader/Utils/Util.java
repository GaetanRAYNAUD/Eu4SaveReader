package eu4SaveReader.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

public final class Util {

    private Util () {
    }

    private static final List<String> mouth = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

    public static final List<String> govRanks = Arrays.asList("", "Duchy", "Kingdom", "Empire");

    public static final List<String> continents = Arrays.asList("Europe", "Asia", "Africa", "North America", "South America", "Oceania", "Indian Ocean");

    public static final List<String> institutions = Arrays.asList("Feudalism", "Renaissance", "Colonialism", "Printing Press", "Global Trade", "Manufactories", "Enlightenment");

    public static String extractInfo (String info, String landmark) {
        int startAddr, endAddr;
        String infoString;

        if ((startAddr = info.indexOf(landmark)) != - 1) {
            startAddr += landmark.length();
            endAddr = info.indexOf("\n", startAddr);
            infoString = info.substring(startAddr, endAddr);
            infoString = infoString.replace("\n", "");
            infoString = infoString.replace("\t", "");
            infoString = infoString.replace("\r", "");

            return infoString;
        }

        return null;
    }

    public static Double extractInfoDouble (String info, String landmark) {
        try {
            return Double.parseDouble(extractInfo(info, landmark));
        } catch (NumberFormatException | NullPointerException e) {
            return 0.;
        }
    }

    public static int extractInfoInt (String info, String landmark) {
        try {
            return Integer.parseInt(extractInfo(info, landmark));
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }

    public static BigDecimal extractInfoBigDecimal (String info, String landmark) {
        try {
            return new BigDecimal(extractInfo(info, landmark));
        } catch (NumberFormatException | NullPointerException e) {
            return new BigDecimal(0);
        }

    }

    public static GregorianCalendar convertStringToDate (String dateString) {
        if (dateString != null) {
            String[] date = dateString.split("\\.");
            return new GregorianCalendar(Integer.parseInt(date[0]), Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));
        }

        return new GregorianCalendar(0, 0, 0);
    }

    public static String printCountryList (ArrayList<String> list) {
        if (list.size() > 0) {
            StringBuilder listString = new StringBuilder();

            for (String l : list) {
                listString.append(Tags.tags.get(l));
                listString.append(", ");
            }

            return listString.toString().substring(0, listString.toString().length() - 2);
        }

        return "None";
    }

    public static String printDate (GregorianCalendar date) {
        if (date.equals(new GregorianCalendar(0, 0, 0))) {
            return "";
        }

        return date.get(GregorianCalendar.DAY_OF_MONTH) + " " + Util.mouth.get(date.get(GregorianCalendar.MONTH)) + " " + date.get(GregorianCalendar.YEAR);
    }

    public static String printBoolean (Boolean bool) {
        return (bool ? "Yes" : "No");
    }
}
