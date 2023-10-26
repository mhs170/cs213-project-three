package transactionmanager;

import java.util.Calendar;


/**
 * Date class for representing dates of birth
 *
 * @author Mohammed Salama, Dakshal Panicker
 */
public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;
    public static final int JAN = 1;
    public static final int FEB = 2;
    public static final int MAR = 3;
    public static final int APR = 4;
    public static final int MAY = 5;
    public static final int JUN = 6;
    public static final int JUL = 7;
    public static final int AUG = 8;
    public static final int SEP = 9;
    public static final int OCT = 10;
    public static final int NOV = 11;
    public static final int DEC = 12;
    public static final int THIRTYONEDAYS = 31;
    public static final int THIRTYDAYS = 30;
    public static final int TWENTYNINEDAYS = 29;
    public static final int TWENTYEIGHTDAYS = 28;
    public static final int TOTALMONTHS = 12;

    private static int currentTestCase = 1;

    /**
     * Create a Date object based off of int parameters
     * @param year integer year
     * @param month integer month
     * @param day integer day
     */
    Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Create a Date object based on datestring
     * @param dateString string
     */
    Date(String dateString) {
        String[] splitDate = dateString.split("/");
        this.month = Integer.parseInt(splitDate[0]);
        this.day = Integer.parseInt(splitDate[1]);
        this.year = Integer.parseInt(splitDate[2]);
    }

    /**
     * Return the Date's year
     * @return integer year
     */
    public int getYear() {
        return year;
    }

    /**
     * Return the Date's day
     * @return integer day
     */
    public int getDay() {
        return day;
    }

    /**
     * Return the Date's month
     * @return integer month
     */
    public int getMonth() {
        return month;
    }

    /**
     * Return if the Date is valid based off of whether the month, year,
     * and day are valid
     * @return true if valid, false if invalid
     */
    public boolean isValid() {
        if (month < 1 || month > TOTALMONTHS) {
            return false;
        }
        if (year < 1900) {
            return false;
        }
        if (day < 1) {
            return false;
        }
        if (month == JAN || month == MAR || month == MAY
                || month == JUL || month == AUG
                || month == OCT || month == DEC) {
            if (day > 31) {
                return false;
            }
        }
        if (month == APR || month == JUN || month == SEP || month == NOV) {
            if (day > 30) {
                return false;
            }
        }
        if (month == FEB) {
            if (!isLeapYear(year)) {
                return day <= 28;
            } else if (isLeapYear(year)) {
                return day <= 29;
            }
        }
        return true;
    }

    /**
     * Return if the inputted year is a leap year
     * @param year integer year to test
     * @return true if leap year, false if non-leap year
     */
    public boolean isLeapYear(int year) {
        //step 1
        if (year % QUADRENNIAL == 0) {
            //step 2
            if (year % CENTENNIAL == 0) {
                //step 3
                if (year % QUATERCENTENNIAL == 0) {
                    return true;
                }
                //step 5
                else {
                    return false;
                }
            }
            //step 4
            else {
                return true;
            }
        }
        //step 5
        else {
            return false;
        }
    }

    /**
     * Return if the Date is today
     * @return true if it is today, false otherwise
     */
    public boolean isToday() {
        Calendar today = Calendar.getInstance();
        int currYear = today.get(Calendar.YEAR);
        int currMonth = today.get(Calendar.MONTH) + 1;
        int currDay = today.get(Calendar.DAY_OF_MONTH);

        Date currDate = new Date(currYear, currMonth, currDay);

        return this.compareTo(currDate) == 0;
    }

    /**
     * Return if the Date is in the future
     * @return true if in future, false if in past
     */
    public boolean isInFuture() {
        Calendar today = Calendar.getInstance();
        int currYear = today.get(Calendar.YEAR);
        int currMonth = today.get(Calendar.MONTH) + 1;
        int currDay = today.get(Calendar.DAY_OF_MONTH);

        Date currDate = new Date(currYear, currMonth, currDay);

        //Check if in future
        return this.compareTo(currDate) > 0;
    }

    public int getYearsSince() {
        if(this.isInFuture()) return -1;

        Calendar today = Calendar.getInstance();
        int currYear = today.get(Calendar.YEAR);
        int currMonth = today.get(Calendar.MONTH) + 1;
        int currDay = today.get(Calendar.DAY_OF_MONTH);

        int diff = currYear - this.year;
        if(this.month > currMonth || (this.month == currMonth && this.day > currDay)) {
            diff--;
        }
        return diff;
    }

    /**
     * Convert a month to a readable string
     * @param month integer month
     * @return String month as word
     */
    private static String monthToString(int month) {
        switch (month) {
            case JAN -> {
                return "January";
            }
            case FEB -> {
                return "February";
            }
            case MAR -> {
                return "March";
            }
            case APR -> {
                return "April";
            }
            case MAY -> {
                return "May";
            }
            case JUN -> {
                return "June";
            }
            case JUL -> {
                return "July";
            }
            case AUG -> {
                return "August";
            }
            case SEP -> {
                return "September";
            }
            case OCT -> {
                return "October";
            }
            case NOV -> {
                return "November";
            }
            case DEC -> {
                return "December";
            }
            default -> {
                return "Unknown month";
            }
        }
    }

    /**
     * Test if this date is equal to another (Object obj)
     * @param obj the date to compareTo
     * @return true if equal, false if not equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Date)) return false;
        Date dateToCompare = (Date) obj;
        return year == dateToCompare.year &&
                month == dateToCompare.month &&
                day == dateToCompare.day;
    }

    /**
     * Compare Date to this date
     * @param dateToCompare the object to be compared.
     * @return >= 1 if this date is before compared date
     * <= 1 if this date is before compared date
     * 0 if this date is equal to compared date
     */
    @Override
    public int compareTo(Date dateToCompare) {
        int compareYears = Integer.compare(year, dateToCompare.year);
        if (compareYears == 0) {
            int compareMonths = Integer.compare(month, dateToCompare.month);
            if (compareMonths == 0) {
                int compareDays = Integer.compare(day, dateToCompare.day);
                return compareDays;
            } else return compareMonths;
        } else return compareYears;
    }

    /**
     * Convert this Date to a String
     * @return String representation of Date
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    //testbed main no longer needed as we are using junit
//    /**
//     * Testbed main, runs tests for date
//     * @param args command line input, unused
//     */
//    public static void main(String[] args) {
//        testLowYear();
//        testNonLeap();
//        testLeap();
//        testMonthOutOfRange();
//        test31Days(); //multiple test cases
//        test30Days(); //multiple test cases
//    }
//
//    /**
//     * Compare test output and expected output, print result of test
//     *
//     * @param date           the date inputted to the test
//     * @param expectedOutput the expected output of the test
//     * @param actualOutput   the actual output of the test
//     */
//    private static void testResult(Date date, boolean expectedOutput,
//                                   boolean actualOutput) {
//        System.out.println("Test input: " + date);
//        System.out.println("Expected output: " + expectedOutput);
//        System.out.println("Actual output: " + actualOutput);
//
//        if (expectedOutput != actualOutput) {
//            System.out.println("(FAIl) \n");
//        } else {
//            System.out.println("(PASS) \n");
//        }
//    }
//
//    /**
//     * Test case: Check if a year starts before 1900
//     */
//    private static void testLowYear() {
//        System.out.printf("** Test case #%d: A year should not start " +
//                "before 1900\n", currentTestCase++);
//
//        Date date = new Date("2/29/0");
//        boolean expected = false;
//        boolean actual = date.isValid();
//        testResult(date, expected, actual);
//    }
//
//
//    /**
//     * Test case: Check if Feb has 28 days in a nonleap year
//     */
//    private static void testNonLeap() {
//        System.out.printf("** Test case #%d: # of days in feb on a non-leap" +
//                " year is 28\n", currentTestCase++);
//
//        Date date = new Date("2/29/2023");
//        boolean expected = false;
//        boolean actual = date.isValid();
//        testResult(date, expected, actual);
//    }
//
//    /**
//     * Test case: # of days in feb on a leap year is 29
//     */
//    private static void testLeap() {
//        System.out.printf("** Test case #%d: # of days in feb on a leap " +
//                "year is 29\n", currentTestCase++);
//
//        Date date = new Date("2/29/2024");
//        boolean expected = true;
//        boolean actual = date.isValid();
//        testResult(date, expected, actual);
//    }
//
//    /**
//     * Test case: The range for a valid month is 1-12
//     */
//    private static void testMonthOutOfRange() {
//        System.out.printf("** Test case #%d: The range for a valid month is" +
//                " 1-12\n", currentTestCase++);
//
//        Date date = new Date("13/21/2020");
//        boolean actual = date.isValid();
//        boolean expected = false;
//        testResult(date, expected, actual);
//    }
//
//    /**
//     * Multiple test cases: For the month, January, March, May, July,
//     * August, October and December, each has 31 days
//     */
//    private static void test31Days() {
//        int[] monthsWith31Days = new int[]{JAN, MAR, MAY, JUL, AUG, OCT, DEC};
//
//        for (int month : monthsWith31Days) {
//            System.out.printf("** Test case #%d: The month %s has 31 days.\n",
//                    currentTestCase++, monthToString(month));
//
//            Date date = new Date(month + "/31/2023");
//            boolean actual = date.isValid();
//            boolean expected = true;
//            testResult(date, expected, actual);
//        }
//    }
//
//    /**
//     * Multiple test cases: For the month, April, June, September and
//     * November, each has 30 days
//     */
//    private static void test30Days() {
//        int[] monthsWith31Days = new int[]{APR, JUN, SEP, NOV};
//
//        for (int month : monthsWith31Days) {
//            System.out.printf("** Test case #%d: The month %s has 30 days.\n",
//                    currentTestCase++, monthToString(month));
//
//            Date date = new Date(month + "/31/2023");
//            boolean actual = date.isValid();
//            boolean expected = false;
//            testResult(date, expected, actual);
//        }
//    }

}