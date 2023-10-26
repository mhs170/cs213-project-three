package transactionmanager;

/**
 * Abstract class that all accounts extend
 *
 * @author Mohammed Salama, Dakshal Panicker
 */
public abstract class Account implements Comparable<Account> {
    protected Profile holder;
    protected double balance;

    public abstract double monthlyInterest();

    public abstract double monthlyFee();

    /**
     * Creates a new account object
     * @param holder Profile information of the account holder
     * @param balance account's balance
     * @param monthlyInterest account's monthly interest
     * @param monthlyFee account's monthly fee
     */
    public Account(Profile holder, double balance, double monthlyInterest,
                   double monthlyFee) {
        this.holder = holder;
        this.balance = balance;
        this.monthlyInterest();
        this.monthlyFee();
    }

    /**
     * Getter method to output holder profile
     * @return account's holder
     */
    public Profile getHolder(){
        return holder;
    }

    /**
     * Getter method to output balance in account
     * @return account's balance
     */
    public double getBalance(){
        return balance;
    }

    /**
     * Getter method to output account's monthly fee
     * @return account's monthly fee
     */
    public double getMonthlyFee(){
        return monthlyFee();
    }

    /**
     * Getter method to output account's monthly interest
     * @return account's monthly interest
     */
    public double getMonthlyInterest(){
        return monthlyInterest();
    }
}