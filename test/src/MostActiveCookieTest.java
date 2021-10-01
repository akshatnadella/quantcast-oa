import org.junit.Test;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import org.junit.Assert;

/**
 * Test class for MostActiveCookie class.
 */
public class MostActiveCookieTest {

    /**
     * Tests individual functionality of the parseFile() method
     * @throws FileNotFoundException
     */
    @Test
    public void testParseFile() throws FileNotFoundException {
        MostActiveCookie.parseFile("C:/Users/nadel/Documents/quantcast/quantcast-oa/main/src/csv/test_log.csv");
        LinkedHashMap<LocalDate, ArrayList<CookieTime<String, LocalTime>>> dateMap = MostActiveCookie.getDateMap();

        HashMap<LocalDate, ArrayList<CookieTime<String, LocalTime>>> expectedDateMap = new HashMap<>();

        ArrayList<CookieTime<String, LocalTime>> temp1 = new ArrayList<>();
        temp1.add(new CookieTime<>("a", LocalTime.parse("14:19:00")));
        temp1.add(new CookieTime<>("b", LocalTime.parse("10:13:00")));
        temp1.add(new CookieTime<>("c", LocalTime.parse("07:25:00")));
        temp1.add(new CookieTime<>("a", LocalTime.parse("06:19:00")));
        expectedDateMap.put(LocalDate.parse("2018-12-09"), temp1);

        ArrayList<CookieTime<String, LocalTime>> temp2 = new ArrayList<>();
        temp2.add(new CookieTime<>("z", LocalTime.parse("22:03:00")));
        temp2.add(new CookieTime<>("f", LocalTime.parse("21:30:00")));
        temp2.add(new CookieTime<>("g", LocalTime.parse("09:30:00")));
        temp2.add(new CookieTime<>("x", LocalTime.parse("23:30:00")));
        expectedDateMap.put(LocalDate.parse("2018-12-08"), temp2);

        Assert.assertEquals(expectedDateMap, dateMap);
    }

    /**
     * Tests individual functionality of the mostActiveOnGivenDate() method
     */
    @Test
    public void testMostActiveOnGivenDate() {
        LinkedHashMap<LocalDate, ArrayList<CookieTime<String, LocalTime>>> dateMap = new LinkedHashMap<>();

        ArrayList<CookieTime<String, LocalTime>> temp1 = new ArrayList<>();
        temp1.add(new CookieTime<>("a", LocalTime.parse("14:19:00")));
        temp1.add(new CookieTime<>("a", LocalTime.parse("10:13:00")));
        temp1.add(new CookieTime<>("a", LocalTime.parse("07:25:00")));
        dateMap.put(LocalDate.parse("2018-12-09"), temp1);

        ArrayList<CookieTime<String, LocalTime>> temp2 = new ArrayList<>();
        temp2.add(new CookieTime<>("d", LocalTime.parse("14:19:00")));
        temp2.add(new CookieTime<>("e", LocalTime.parse("10:13:00")));
        temp2.add(new CookieTime<>("f", LocalTime.parse("07:25:00")));
        dateMap.put(LocalDate.parse("2018-12-08"), temp2);

        MostActiveCookie.setDateMap(dateMap);

        String output1 = MostActiveCookie.mostActiveOnGivenDate("2018-12-08");
        Assert.assertEquals("d e f", output1);

        String output2 = MostActiveCookie.mostActiveOnGivenDate("2018-12-09");
        Assert.assertEquals("a", output2);
    }

    /**
     * Tests overall functionality by providing command line input
     * @throws FileNotFoundException
     */
    @Test
    public void testMain() throws FileNotFoundException {
        String[] testArgs = {"C:/Users/nadel/Documents/quantcast/quantcast-oa/main/src/csv/test_log.csv", "-d", "2018-12-08"};
        MostActiveCookie.main(testArgs);
        /* Expected Output:
        z
        f
        g
        x  */
    }
}
