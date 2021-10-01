import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Command line program that processes a log file and returns the most active
 * cookie for a specified day.
 */
public class MostActiveCookie {

    /**
     * Hash map that stores dates as keys and a list of CookieTimes as values.
     * This allows us to easily query the cookies for a given day and then
     * calculate the most active cookie from that list.
     */
    private static LinkedHashMap<LocalDate, ArrayList<CookieTime<String, LocalTime>>> dateMap = new LinkedHashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        parseFile(args[0]);
        String flag = args[1];
        if (flag.equals("-d")) {
            mostActiveOnGivenDate(args[2]);
        } else {
            System.out.println(flag + " is not valid.");
        }
    }

    /**
     * Helper method that parses the given log file and updates a hash map that
     * contains dates as keys and cookie ids as values.
     * @param fileString path to log file we want to parse
     * @throws FileNotFoundException thrown if given file does not exist
     */
    public static void parseFile(String fileString) throws FileNotFoundException {
        if (fileString == null) {
            System.out.println("File path cannot be null.");
            throw new NullPointerException();
        }

        File file = new File(fileString);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String cookie = line.split(",")[0];
            String dateString = line.split(",")[1].substring(0, 10);
            String timeString = line.split(",")[1].substring(11, 19);
            LocalDate date = LocalDate.parse(dateString);
            ArrayList<CookieTime<String, LocalTime>> temp;
            if (!dateMap.containsKey(date)) {
                temp = new ArrayList<>();
            } else {
                temp = dateMap.get(date);
            }
            temp.add(new CookieTime<>(cookie, LocalTime.parse(timeString)));
            dateMap.put(date, temp);
        }
    }

    /**
     * Finds the most active cookie on the given date.
     * @param dateString date we want to find most active cookie for
     * @return all cookies that have the max number of occurrences
     */
    public static String mostActiveOnGivenDate(String dateString) {
        if (dateString == null) {
            System.out.println("Date cannot be null.");
            throw new NullPointerException();
        }
        LocalDate date = LocalDate.parse(dateString);
        if (!dateMap.containsKey(date)) {
            System.out.println("Given date cannot be found.");
            throw new IllegalArgumentException();
        }

        /*
        Query the cookies for the given date and then iterate through that
        list to build a linked hash map (to preserve order) with cookie ids
        as keys and number of occurrences as values.
        */
        ArrayList<CookieTime<String, LocalTime>> cookiesOnDate = dateMap.get(date);
        LinkedHashMap<String, Integer> occurrenceMap = new LinkedHashMap<>();
        for (CookieTime<String, LocalTime> ct : cookiesOnDate) {
            Integer value = occurrenceMap.get(ct.getCookie());
            if (value == null) {
                occurrenceMap.put(ct.getCookie(), 1);
            } else {
                occurrenceMap.put(ct.getCookie(), value + 1);
            }
        }

        return getMaxOccurring(occurrenceMap);
    }

    /**
     * Helper method that prints and returns all cookies that have the max number of occurrences
     * given a linked hash map with cookies as keys and occurrences as values.
     * @param occurrenceMap linked hash map with cookies as keys and occurrences as values
     * @return all cookies that have the max number of occurrences
     */
    private static String getMaxOccurring(LinkedHashMap<String, Integer> occurrenceMap) {
        /*
        Iterate through the occurrence map to find the max number of occurrences.
         */
        Map.Entry<String, Integer> max = null;
        for (Map.Entry<String, Integer> e : occurrenceMap.entrySet()) {
            if (max == null || e.getValue() >= max.getValue()) {
                max = e;
            }
        }

        /*
        Print/return all cookies that have the max number of occurrences.
         */
        String output = "";
        for (Map.Entry<String, Integer> e : occurrenceMap.entrySet()) {
            if (e.getValue().equals(max.getValue())) {
                System.out.println(e.getKey());
                output += e.getKey() + " ";
            }
        }

        return output.substring(0, output.length() - 1);
    }

    /**
     * Getter method for dateMap, used for testing
     * @return dateMap
     */
    public static LinkedHashMap<LocalDate, ArrayList<CookieTime<String, LocalTime>>> getDateMap() {
        return dateMap;
    }

    /**
     * Setter method for dateMap, used for testing
     * @param dateMap hash map to update dateMap with
     */
    public static void setDateMap(LinkedHashMap<LocalDate, ArrayList<CookieTime<String, LocalTime>>> dateMap) {
        MostActiveCookie.dateMap = dateMap;
    }

}
