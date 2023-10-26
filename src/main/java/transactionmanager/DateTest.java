package transactionmanager;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Contains unit tests for Date class
 *
 * @author Mohammed Salama, Dakshal Panicker
 */
public class DateTest {

    //5 invalid cases, 2 valid cases total


    //Test leap years

    @Test
    public void testDaysInFebLeap() {
        Date date = new Date("2/29/2008");
        assertTrue(date.isValid()); //valid case
    }

    @Test
    public void testDaysInFebNonLeap() {
        Date date = new Date("2/29/2011");
        assertFalse(date.isValid());
    }

    //Test year parameter

    @Test
    public void testYearAfter1900() {
        Date date = new Date("1/1/1800");
        assertFalse(date.isValid());
    }

    //Test month parameter

    @Test
    public void testMonthWith31Days() {
        Date date = new Date("1/31/2008");
        assertTrue(date.isValid());  //valid case
    }

    @Test
    public void testMonthWith30Days() {
        Date date = new Date("4/31/2008");
        assertFalse(date.isValid());
    }

    @Test
    public void testMonthGreaterThanZero() {
        Date date = new Date("0/1/2000");
        assertFalse(date.isValid());
    }

    //Test day parameter

    @Test
    public void testDayGreaterThanZero() {
        Date date = new Date("1/0/2000");
        assertFalse(date.isValid());
    }


}