package transactionmanager;

/**
 * Checking account class with constants for different fees and rates
 *
 * @author Mohammed Salama, Dakshal Panicker
 */
public class Checking extends Account {
    public static final double ANNUAL_INTEREST_RATE = 0.01;
    public static final double MONTHLY_FEE = 12;
    public static final double BALANCE_TO_WAIVE_FEE = 1000;
    public static final int MONTHS_IN_YEAR = 12;

    /**
     * Creates a new checking account
     *
     * @param holder  Profile information of the account holder
     * @param balance account's balance
     */
    public Checking(Profile holder, double balance) {
        super(holder, balance, ANNUAL_INTEREST_RATE, MONTHLY_FEE);
    }

    /**
     * Method to calculate monthly interest
     *
     * @return account's monthly interest
     */
    @Override
    public double monthlyInterest() {
        return (ANNUAL_INTEREST_RATE * balance) / MONTHS_IN_YEAR;
    }

    /**
     * Method to set the account's monthly fee
     *
     * @return account's monthly fee, 0 if balance >= $1000
     */
    @Override
    public double monthlyFee() {
        if (balance < BALANCE_TO_WAIVE_FEE) {
            return MONTHLY_FEE;
        }
        return 0;
    }

    /**
     * Method to compare if objects are equal
     * @param obj the account to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Checking)) {
            return false;
        }
        if(obj instanceof CollegeChecking) {
            return false;
        }
        Checking compareThis = (Checking) obj;
        return this.getHolder().equals(compareThis.getHolder());
    }

    /**
     * Method to compare objects
     * @param o the object to be compared.
     * @return -1 if this is before object, 0 if this is equivalent, 1 if
     * higher than object
     */
    @Override
    public int compareTo(Account o) {
        return 0;
    }

    /**
     * Method to print checking accounts with proper formatting
     * @return String that displays account info
     */
    @Override
    public String toString() {
        return String.format("Checking::%s %s %s::Balance $%,.2f",
                holder.getFname(), holder.getLname(), holder.getDob(),
                getBalance());
    }
    //Checking::Jason Brown 3/31/1998::Balance $1,200.00
}
