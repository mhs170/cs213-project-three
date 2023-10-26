package transactionmanager;

/**
 * Savings account class with constants for different fees and rates
 *
 * @author Mohammed Salama, Dakshal Panicker
 */
public class Savings extends Account {
    protected boolean isLoyal; //loyal customer status
    public static final double ANNUAL_INTEREST_RATE = 0.04;
    public static final double MONTHLY_FEE = 25;
    public static final double INTEREST_IF_LOYAL = 0.0425;
    public static final double BALANCE_TO_WAIVE_FEE = 500;
    public static final int MONTHS_IN_YEAR = 12;

    /**
     * Creates a new saving's account
     *
     * @param holder  Profile information of the account holder
     * @param balance account's balance
     */
    public Savings(Profile holder, double balance, boolean isLoyal) {
        super(holder, balance, ANNUAL_INTEREST_RATE, MONTHLY_FEE);
        this.isLoyal = isLoyal;
    }

    /**
     * Method to calculate and set account's monthly Interest
     *
     * @return account's monthly interest, 4% if not a loyal customer, 4
     * .25% if is a loyal customer
     */
    @Override
    public double monthlyInterest() {
        if (isLoyal) {
            return (INTEREST_IF_LOYAL * balance) / MONTHS_IN_YEAR;
        }
        return (ANNUAL_INTEREST_RATE * balance) / MONTHS_IN_YEAR;
    }

    /**
     * Method to set account's monthly Fee
     *
     * @return account's monthly fee, 0 if balance >= $500
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
     *
     * @param obj the account to compare
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Savings)) {
            return false;
        }
        if (obj instanceof MoneyMarket) {
            return false;
        }
        Savings compareThis = (Savings) obj;
        return this.getHolder().equals(compareThis.getHolder());
    }

    @Override
    public int compareTo(Account o) {
        return 0;
    }

    /**
     * Method to display savings accounts with proper formatting
     *
     * @return String that displays account info
     */
    @Override
    public String toString() {
        if (isLoyal) {
            return String.format("Savings::%s %s %s::Balance $%,.2f::is " +
                            "loyal",
                    holder.getFname(), holder.getLname(), holder.getDob(),
                    getBalance());
        }
        return String.format("Savings::%s %s %s::Balance $%,.2f",
                holder.getFname(), holder.getLname(), holder.getDob(),
                getBalance());
    }
    //Savings::Jane Doe 10/1/1995::Balance $1,000.00
}
