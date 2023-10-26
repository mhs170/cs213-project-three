package transactionmanager;

/**
 * Checking account class with constants for different fees and rates
 *
 * @author Mohammed Salama, Dakshal Panicker
 */
public class CollegeChecking extends Checking {
    private Campus campus; //campus code
    public static final double ANNUAL_INTEREST_RATE = 0.01;
    public static final double MONTHLY_FEE = 0;

    /**
     * Creates a new college checking account
     *
     * @param holder  Profile information of the account holder
     * @param balance Balance of the college checking account
     * @param campus  Campus code associated with account
     */
    public CollegeChecking(Profile holder, double balance, Campus campus) {
        super(holder, balance);
        this.campus = campus;
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
        if (!(obj instanceof CollegeChecking)) {
            return false;
        }
        Checking compareThis = (Checking) obj;
        return this.getHolder().equals(compareThis.getHolder());
    }

    /**
     * Overrides monthly Fee in checking
     * @return 0 as monthlyFee
     */
    @Override
    public double monthlyFee(){
        return 0;
    }

    /**
     * Method to display college checking accounts with proper formatting
     *
     * @return String that displays account info
     */
    @Override
    public String toString() {
        return String.format("College Checking::%s %s %s::Balance $%,.2f::%s",
                holder.getFname(), holder.getLname(), holder.getDob(),
                getBalance(), campus);
    }
    //College Checking::Roy Brooks 10/31/1999::Balance $2,909.10::NEWARK
}
