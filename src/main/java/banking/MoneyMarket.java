package banking;

/**
 * Money Market class with constants for different fees and rates
 *
 * @author Mohammed Salama, Dakshal Panicker
 */
public class MoneyMarket extends Savings {
    private int withdrawal; //number of withdrawals
    public static final double ANNUAL_INTEREST_RATE_IF_NOT_LOYAL = 0.045;
    public static final double ANNUAL_INTEREST_RATE_IF_LOYAL = 0.0475;
    public static final double MONTHLY_FEE = 25;
    public static final double AMOUNT_TO_WAIVE_FEE = 2000;
    public static final int THREE_WITHDRAWALS = 3;
    public static final int TEN_DOLLAR_FEE = 10;

    /**
     * Creates a new money market savings account
     *
     * @param holder     Profile information of the account holder
     * @param balance    Balance of the college checking account
     * @param withdrawal number of withdrawals
     */
    public MoneyMarket(Profile holder, double balance, boolean isLoyal,
                       int withdrawal) {
        super(holder, balance, isLoyal);
        this.withdrawal = withdrawal;
    }

    /**
     * Method to output number of withdrawals
     * @return number of withdrawals
     */
    public int getWithdrawal(){
        return this.withdrawal;
    }

    /**
     * Method to set and update number of withdrawals
     * @param withdrawal number of withdrawals
     */
    public void setWithdrawals(int withdrawal){
        this.withdrawal = withdrawal;
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
        if (!(obj instanceof MoneyMarket)) {
            return false;
        }
        MoneyMarket compareThis = (MoneyMarket) obj;
        return this.getHolder().equals(compareThis.getHolder());
    }

    /**
     * Method to calculate a money market savings account's interest
     * @return monthly interest
     */
    @Override
    public double monthlyInterest() {
        if (isLoyal) {
            return (ANNUAL_INTEREST_RATE_IF_LOYAL * balance) / MONTHS_IN_YEAR;
        }
        return (ANNUAL_INTEREST_RATE_IF_NOT_LOYAL * balance) / MONTHS_IN_YEAR;
    }

    /**
     * Sets monthly fee to 0 if balance >= 2000
     * @return monthly fee
     */
    @Override
    public double monthlyFee(){
        double monthlyFee = MONTHLY_FEE;
        if (balance >= AMOUNT_TO_WAIVE_FEE){
            monthlyFee = 0;
            isLoyal = true;
        }
        if (getWithdrawal() > THREE_WITHDRAWALS){
            monthlyFee += TEN_DOLLAR_FEE;
        }
        return monthlyFee;
    }

    /**
     * Method to display Money Market accounts with proper formatting
     *
     * @return String that displays account info
     */
    @Override
    public String toString() {
        if (balance >= 2000) {
            return String.format("Money Market::Savings::%s %s %s::Balance " +
                            "$%,.2f::is loyal::withdrawal: %d",
                    holder.getFname(), holder.getLname(), holder.getDob(),
                    getBalance(), withdrawal);
        }
        return String.format("Money Market::Savings::%s %s %s::Balance $%," +
                        ".2f::withdrawal: %d",
                holder.getFname(), holder.getLname(), holder.getDob(),
                getBalance(), withdrawal);
    }
    //Money Market::Savings::Roy Brooks 10/31/1979::Balance $2,909.10::is
    // loyal::withdrawal: 0
}
