package banking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Scanner;

/**
 * This controller class handles events from the UI.
 * @author Mohammed Salama, Dakshal Panicker
 */
public class TransactionManagerController {

    private AccountDatabase database;

    @FXML
    private ChoiceBox<String> actionPicker;

    @FXML
    private ChoiceBox<String> accountTypePicker;

    @FXML
    private Text campusLabel;

    @FXML
    private ChoiceBox<String> campusPicker;

    @FXML
    private DatePicker dobPicker;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private CheckBox loyaltyCheckbox;

    @FXML
    private Text amountLabel;

    @FXML
    private TextField amountField;

    @FXML
    private Button submitButton;

    @FXML
    private Button clearButton;

    @FXML
    private TextArea output;

    //** SECOND TAB **//

    @FXML
    private Button printAllButton;

    @FXML
    private Button printInterestsAndFeesButton;

    @FXML
    private Button updateInterestsAndFeesButton;

    @FXML
    private Button loadFromFileButton;

    @FXML
    private TextArea outputTwo;

    /**
     * Setup method to initialize accountdatabase and dropdowns
     */
    @FXML
    public void initialize() {

        //create database
        database = new AccountDatabase(new Account[]{}, 0);

        //initialize ChoiceBox dropdowns

        String[] actionTypes = {
                "Open", "Close", "Deposit", "Withdraw"
        };
        String[] accountTypes = {
                "Checking", "College Checking", "Savings", "Money Market"
        };
        String[] campusTypes = {
                "New Brunswick", "Newark", "Camden"
        };

        //initialize action type dropdown
        actionPicker.getItems().addAll(actionTypes);
        //default value
        actionPicker.setValue("Open");
        //register handler manually
        actionPicker.setOnAction(this::onActionSelect);

        //initialize account type dropdown
        accountTypePicker.getItems().addAll(accountTypes);
        accountTypePicker.setValue("Checking");
        accountTypePicker.setOnAction(this::onAccountSelect);

        //initialize campus dropdown
        campusPicker.getItems().addAll(campusTypes);
        campusPicker.setValue("New Brunswick");

    }

    /**
     * Event handler for action-select dropdown
     * Allows users to choose from "open" "close" "deposit" "withdraw"
     * @param event the dropdown select event
     */
    public void onActionSelect(ActionEvent event) {
        String action = actionPicker.getValue();

        //clear the form
        clear(event);

        //change the ui to match which action is selected
        //make fields visible or invisible
        switch (action) {
            case "Open" -> {
                campusPicker.setVisible(true);
                campusLabel.setVisible(true);
                loyaltyCheckbox.setVisible(true);
                amountLabel.setVisible(true);
                amountField.setVisible(true);
                amountLabel.setText("Initial Amount");
            }
            case "Close" -> {
                campusPicker.setVisible(false);
                campusLabel.setVisible(false);
                loyaltyCheckbox.setVisible(false);
                amountLabel.setVisible(false);
                amountField.setVisible(false);
            }
            case "Deposit" -> {
                campusPicker.setVisible(false);
                campusLabel.setVisible(false);
                loyaltyCheckbox.setVisible(false);
                amountLabel.setVisible(true);
                amountField.setVisible(true);
                amountLabel.setText("Deposit Amount");
            }
            case "Withdraw" -> {
                campusPicker.setVisible(false);
                campusLabel.setVisible(false);
                loyaltyCheckbox.setVisible(false);
                amountLabel.setVisible(true);
                amountField.setVisible(true);
                amountLabel.setText("Withdraw Amount");
            }
        }
    }

    /**
     * Event handler for account select dropdown
     * @param event the choicebox select event
     */
    public void onAccountSelect(ActionEvent event) {
        String selectedType = accountTypePicker.getValue();

        //enable campus picker for a college checking account
        if (selectedType.equals("College Checking")) {
            campusPicker.setDisable(false);
        } else {
            campusPicker.setDisable(true);
            campusPicker.setValue("New Brunswick");
        }

        //enable isLoyal checkbox for a savings account
        if (selectedType.equals("Savings")) {
            loyaltyCheckbox.setDisable(false);
        } else {
            loyaltyCheckbox.setDisable(true);
            loyaltyCheckbox.setSelected(false);
        }
    }

    /**
     * Event handler for clear button, clears the form
     * @param event button select event
     */
    @FXML
    public void clear(ActionEvent event) {
        firstNameField.clear();
        lastNameField.clear();
        dobPicker.getEditor().clear();
        dobPicker.setValue(null);
        accountTypePicker.setValue("Checking");
        campusPicker.setValue("New Brunswick");
        loyaltyCheckbox.setSelected(false);
        amountField.clear();
    }

    /**
     * Event handler for submit button, submits form and
     * runs the resulting logic
     * @param event button click event
     */
    @FXML
    public void submit(ActionEvent event) {
        try {
            String action = actionPicker.getValue();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String dobString = dobPicker.getEditor().getText();
            String accountType = accountTypePicker.getValue();
            String campus = campusPicker.getValue();
            boolean isLoyal = loyaltyCheckbox.isSelected();
            String amountStr = amountField.getText();

            //check if names are valid (not empty)
            if (firstName.isEmpty()) {
                print(output, "Please enter a first name");
                return;
            }
            if (lastName.isEmpty()) {
                print(output, "Please enter a last name");
                return;
            }

            //create date and check if valid
            if (dobString.isEmpty()) {
                print(output, "Please enter a date of birth.");
                return;
            }
            Date dob = createDate(dobString);
            if (!dateIsValid(dob, dobString)) return;

            double amount = 0.0;
            if (!action.equals("Close")) {
                //parse amount (throws exception)
                if (amountStr.isEmpty()) {
                    print(output, "Please enter a valid amount.");
                    return;
                }
                amount = Double.parseDouble(amountStr);
            }

            //run action
            switch (action) {
                case "Open" -> OpenAccount(
                        firstName,
                        lastName,
                        dob,
                        accountType,
                        campus,
                        isLoyal,
                        amount
                );
                case "Close" -> CloseAccount(
                        firstName,
                        lastName,
                        dob,
                        accountType
                );
                case "Deposit" -> DepositToAccount(
                        firstName,
                        lastName,
                        dob,
                        amount,
                        accountType
                );
                case "Withdraw" -> WithdrawFromAccount(
                        firstName,
                        lastName,
                        dob,
                        amount,
                        accountType
                );
            }
        } catch (NumberFormatException exp) {
            print(output, "Not a valid amount.");
        }
    }

    //*** general helper methods ***//

    /**
     * Print out a string by appending it to a TEXT AREA
     *
     * @param output the TextArea to be appended to
     * @param string the string to be printed
     */
    public void print(TextArea output, String string) {
        output.appendText(string + "\n");
    }

    /**
     * Print out a string by appending it to a TEXT AREA
     *
     * @param output       the TextArea to be appended to
     * @param formatString the string to be formatted and printed
     * @param args         the arguments used to format the string
     */
    public void printFormatted(TextArea output, String formatString,
                               Object... args) {
        String outputString = String.format(formatString, args) + "\n";
        output.appendText(outputString);
    }

    public String abbreviateType(String accountType) {
        return switch (accountType) {
            case "Checking" -> "C";
            case "College Checking" -> "CC";
            case "Savings" -> "S";
            case "Money Market" -> "MM";
            default -> "Incorrect type";
        };
    }

    /**
     * Print some account status
     *
     * @param profile     the profile of the account
     * @param accountType the account type
     * @param status      the status to print afterwards
     */
    private void printStatus(
            Profile profile, String accountType, String status
    ) {
        printFormatted(output, "%s %s %s(%s) %s",
                profile.getFname(),
                profile.getLname(),
                profile.getDob(),
                abbreviateType(accountType),
                status);
    }


    /**
     * Create and return Date object based on mm/dd/yyyy string input.
     * If input is invalid, then return null.
     *
     * @param date a string in mm/dd/yyyy format
     * @return Date object if valid date string, null if invalid
     */
    private Date createDate(String date) {
        String dateFormatRegex =
                "^(\\d{1,2}(/)\\d{1,2}(/)\\d{4})$";
        if (!date.matches(dateFormatRegex)) return null;

        String[] splitDate = date.split("/");
        int month = Integer.parseInt(splitDate[0]);
        int day = Integer.parseInt(splitDate[1]);
        int year = Integer.parseInt(splitDate[2]);

        Date dateObj = new Date(year, month, day);
        if (!dateObj.isValid()) return null;

        return dateObj;
    }

    /**
     * Validate a date
     *
     * @param date the date to validate
     * @return true if valid, false otherwise
     */
    private boolean dateIsValid(Date date, String dateStr) {
        if (date == null) {
            printFormatted(output, "DOB invalid: %s not a" +
                    " valid calendar date!", dateStr);
            return false;
        }

        if (date.isToday() || date.isInFuture()) {
            printFormatted(output, "DOB invalid: %s cannot" +
                    " be today or a future day.", dateStr);
            return false;
        }
        return true;
    }

    //*** methods for opening an account ***//

    /**
     * Print that the account is opened, or it's already in database
     *
     * @param profile     the profile of the account
     * @param accountType the account type
     */
    private void printOpenStatus(
            Profile profile, String accountType, boolean opened
    ) {
        String status;
        if (opened) status = "opened.";
        else status = "is already in the database.";
        printStatus(profile, accountType, status);
    }

    /**
     * Creates an account
     *
     * @param firstName      string first name
     * @param lastName       string last name
     * @param dateOfBirth    date of birth
     * @param accountType    type of account
     * @param campus         string campus
     * @param isLoyal        boolean is loyal
     * @param initialDeposit initial deposit as double
     */
    private void OpenAccount(
            String firstName,
            String lastName,
            Date dateOfBirth,
            String accountType,
            String campus,
            boolean isLoyal,
            Double initialDeposit
    ) {
        if (dateOfBirth.getYearsSince() < 16) {
            print(output, "DOB invalid: " + dateOfBirth + " under 16.");
            return;
        }
        if (initialDeposit <= 0) {
            print(output, "Initial deposit cannot be 0 or negative" +
                    ".");
            return;
        }
        if (accountType.equals("Money Market") && initialDeposit < 2000.00) {
            print(output,
                    "Minimum of $2000 to open a Money Market account."
            );
            return;
        }
        if (accountType.equals("College Checking")){
            if (!underTwentyFour(dateOfBirth)){
                print(output, "DOB invalid: " + dateOfBirth + " over 24.");
                return;
            }
        }

        Profile profile = new Profile(firstName, lastName, dateOfBirth);
        Account account = switch (accountType) {
            case "Checking" -> new Checking(
                    profile, initialDeposit
            );
            case "College Checking" -> new CollegeChecking(
                    profile, initialDeposit,
                    switch (campus) {
                        case "New Brunswick" -> Campus.NEW_BRUNSWICK;
                        case "Newark" -> Campus.NEWARK;
                        case "Camden" -> Campus.CAMDEN;
                        default -> {
                            print(output, "Invalid campus.");
                            yield null;
                        }
                    }
            );
            case "Savings" -> new Savings(
                    profile, initialDeposit, isLoyal
            );
            case "Money Market" -> new MoneyMarket(
                    profile, initialDeposit, true, 0
            );
            default -> null;
        };
        if (account == null) {
            print(output, "Invalid account type");
            return;
        }
        boolean openSuccess = database.open(account);
        printOpenStatus(profile, accountType, openSuccess);
    }

    //*** methods for closing an account ***//

    /**
     * Method that prints result of close method
     *
     * @param profile     Profile associated with account
     * @param accountType type of account
     * @param closed      if account is closed, true
     */
    private void printCloseStatus(
            Profile profile, String accountType, boolean closed
    ) {
        if (closed) {
            String status = "has been closed.";
            printStatus(profile, accountType, status);
        }
    }

    /**
     * Close an account
     *
     * @param firstName   string first name
     * @param lastName    string last name
     * @param dateOfBirth date of birth
     * @param accountType type of account
     */
    private void CloseAccount(
            String firstName,
            String lastName,
            Date dateOfBirth,
            String accountType
    ) {
        Profile holder = new Profile(firstName, lastName, dateOfBirth);
        Account account = switch (accountType) {
            case "Checking" -> new Checking(holder, 0);
            case "College Checking" -> new CollegeChecking(
                    holder, 0, Campus.NEWARK
            );
            case "Savings" -> new Savings(
                    holder, 0, false
            );
            case "Money Market" -> new MoneyMarket(
                    holder, 0, false, 0);
            default -> null;
        };
        if (account == null) {
            print(output, "Invalid account type");
            return;
        }
        if (!database.contains(account)) {
            printStatus(holder, accountType, "is not in the database.");
            return;
        }
        boolean closeSuccess = database.close(account);
        printCloseStatus(holder, accountType, closeSuccess);
    }

    //*** methods for depositing to an account ***//

    /**
     * Deposit to an account
     *
     * @param firstName   string first name
     * @param lastName    string last name
     * @param dateOfBirth date of birth
     * @param amount      the amount to deposit
     * @param accountType type of account
     */
    private void DepositToAccount(
            String firstName,
            String lastName,
            Date dateOfBirth,
            double amount,
            String accountType
    ) {
        if (amount <= 0) {
            print(output, "Deposit - amount cannot be" +
                    " 0 or negative.");
            return;
        }

        Profile dummyProfile = new Profile(firstName, lastName,
                dateOfBirth);
        Account dummyAccount = switch (accountType) {
            case "Checking" -> new Checking(
                    dummyProfile, amount
            );
            case "College Checking" -> new CollegeChecking(
                    dummyProfile, amount, Campus.NEWARK
            );
            case "Savings" -> new Savings(
                    dummyProfile, amount, false
            );
            case "Money Market" -> new MoneyMarket(
                    dummyProfile, amount, false, 0
            );
            default -> null;
        };
        if (dummyAccount == null) {
            print(output, "Invalid account type");
            return;
        }
        if (!(database.contains(dummyAccount))) {
            printStatus(dummyProfile, accountType,
                    "is not in the database.");
            return;
        }
        database.deposit(dummyAccount);
        printStatus(
                dummyProfile, accountType,
                "Deposit - balance updated.")
        ;
    }

    //*** methods for withdrawing from an account ***//

    /**
     * Print that the account is opened, or it's already in database
     *
     * @param profile     the profile of the account
     * @param accountType the account type
     */
    private void printWithdrawStatus(
            Profile profile, String accountType, boolean withdrawSuccess
    ) {
        String status;
        if (withdrawSuccess) status = "Withdraw - balance updated.";
        else status = "Withdraw - insufficient fund.";
        printStatus(profile, accountType, status);
    }

    /**
     * Withdraw from an account
     *
     * @param firstName   string first name
     * @param lastName    string last name
     * @param dateOfBirth date of birth
     * @param amount      the amount to withdraw
     * @param accountType type of account
     */
    private void WithdrawFromAccount(
            String firstName,
            String lastName,
            Date dateOfBirth,
            double amount,
            String accountType
    ) {
        if (amount <= 0) {
            print(output, "Withdraw -" +
                    " amount cannot be 0 or negative.");
            return;
        }

        Profile profile = new Profile(firstName, lastName, dateOfBirth);
        Account dummy;
        switch (accountType) {
            case "Checking" -> dummy = new Checking(
                    profile, amount);
            case "College Checking" -> dummy = new CollegeChecking(
                    profile, amount, Campus.NEWARK);
            case "Savings" -> dummy = new Savings(
                    profile, amount, false);
            case "Money Market" -> dummy = new MoneyMarket(
                    profile, amount, false, 0);
            default -> {
                return;
            }
        }

        if (!(database.contains(dummy))) {
            printStatus(profile, accountType,
                    "is not in the database.");
            return;
        }

        boolean isSuccess = database.withdraw(dummy);
        printWithdrawStatus(profile, accountType, isSuccess);
    }


    //*** SECOND TAB ***//

    /**
     * Method to display all the accounts in the account database, sorted
     * by the account types. For the same account type, sort by the account
     * holder’s profile (last name, first name and dob.)
     */
    public void printAll() {
        //runs when printAll button is clicked

        //print to output on second tab
        print(outputTwo, database.printSorted());


    }

    /**
     * Method to display all the accounts in the account database, the same
     * order as the P command. In addition, display the calculated fees
     * and monthly interests based on current account balances.
     */
    public void printInterestsAndFees() {
        print(outputTwo, database.printFeesAndInterests());
    }

    /**
     * Method to update and display the account balance for all accounts by
     * applying the fees and interests earned.
     */
    public void updateInterestsAndFees() {
        print(outputTwo, database.printUpdatedBalances());
    }

    public void loadFromFile() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select text file to use");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        File file = fileChooser.showOpenDialog(loadFromFileButton.getScene().getWindow());
        if(file == null) {
            print(outputTwo, "No file selected.");
            return;
        }
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] input = line.split(",");
            if (input.length < 4) {
                continue;
            }
            String accountType = input[0];
            String fName = input[1];
            String lName = input[2];
            String dob = input[3];
            Date dateOfBirth = createDate(dob);
            double initialDeposit = 0;
            if (input.length > 4) {
                initialDeposit = Double.parseDouble(input[4]);
            }
            switch (accountType) {
                case "C" ->
                        OpenCheckingAccount(fName, lName, dateOfBirth,
                                initialDeposit, database);
                case "CC" -> {
                    int campusCode = Integer.parseInt(input[5]);
                    OpenCollegeCheckingAccount(fName, lName, dateOfBirth,
                            initialDeposit, campusCode, database);
                }
                case "S" -> {
                    int loyalCustomer = Integer.parseInt(input[5]);
                    OpenSavingsAccount(fName, lName, dateOfBirth,
                            initialDeposit, loyalCustomer, database);
                }
                case "MM" ->
                        OpenMoneyMarketAccount(fName, lName, dateOfBirth,
                                initialDeposit, database);
            }
        }
        sc.close();
        //print all accounts to show that they have been loaded
        printAll();
    }

    /**
     * Create checking account
     *
     * @param firstName      first name of user
     * @param lastName       last name of user
     * @param dateOfBirth    DOB of user, in past
     * @param initialDeposit initial deposit for account
     */
    private void OpenCheckingAccount(
            String firstName,
            String lastName,
            Date dateOfBirth,
            double initialDeposit,
            AccountDatabase database
    ) {
        Profile profile = new Profile(firstName, lastName, dateOfBirth);
        Checking account = new Checking(profile, initialDeposit);
        boolean openSuccess = database.open(account);
        printOpenStatus(profile, "C", openSuccess);
    }

    /**
     * Create college checking account
     *
     * @param firstName      first name of user
     * @param lastName       last name of user
     * @param dateOfBirth    DOB of user, in past
     * @param initialDeposit initial deposit for account
     * @param campusCode     0, 1, 2 codes for different campuses
     */
    private void OpenCollegeCheckingAccount(
            String firstName,
            String lastName,
            Date dateOfBirth,
            double initialDeposit,
            int campusCode,
            AccountDatabase database
    ) {

        Campus campus;
        switch (campusCode) {
            case 0 -> campus = Campus.NEW_BRUNSWICK;
            case 1 -> campus = Campus.NEWARK;
            case 2 -> campus = Campus.CAMDEN;
            default -> {
                print(output, "Invalid campus code.");
                return;
            }
        }
        Profile profile = new Profile(firstName, lastName, dateOfBirth);
        CollegeChecking account = new CollegeChecking(profile,
                initialDeposit, campus);
        boolean openSuccess = database.open(account);
        printOpenStatus(profile, "CC", openSuccess);
    }

    /**
     * Create savings account
     *
     * @param firstName      first name of user
     * @param lastName       last name of user
     * @param dateOfBirth    DOB of user, in past
     * @param initialDeposit initial deposit for account
     * @param loyalCustomer  0 or 1 based on whether customer is loyal
     */
    private void OpenSavingsAccount(
            String firstName,
            String lastName,
            Date dateOfBirth,
            double initialDeposit,
            int loyalCustomer,
            AccountDatabase database
    ) {
        boolean isLoyal;
        if (loyalCustomer == 0) {
            isLoyal = false;
        } else if (loyalCustomer == 1) {
            isLoyal = true;
        } else {
            print(output, "Invalid command.");
            return;
        }
        Profile profile = new Profile(firstName, lastName, dateOfBirth);
        Savings account = new Savings(profile, initialDeposit, isLoyal);
        boolean openSuccess = database.open(account);
        printOpenStatus(profile, "S", openSuccess);
    }

    /**
     * Create money market account
     *
     * @param firstName      first name of user
     * @param lastName       last name of user
     * @param dateOfBirth    DOB of user, in past
     * @param initialDeposit initial deposit for account
     */
    private void OpenMoneyMarketAccount(
            String firstName,
            String lastName,
            Date dateOfBirth,
            double initialDeposit,
            AccountDatabase database
    ) {
        if (initialDeposit < 2000.00) {
            print(output, "Minimum of $2000 to open a Money Market account.");
            return;
        }
        Profile profile = new Profile(firstName, lastName, dateOfBirth);
        MoneyMarket account = new MoneyMarket(profile, initialDeposit,
                true, 0);
        boolean openSuccess = database.open(account);
        printOpenStatus(profile, "MM", openSuccess);
    }

    /**
     * Method to test if account holder is under 24
     *
     * @param dob date of birth
     * @return true if under 24, false otherwise
     */
    private boolean underTwentyFour(Date dob) {
        Calendar today = Calendar.getInstance();
        int currentYear = today.get(Calendar.YEAR);
        int currentMonth = today.get(Calendar.MONTH) + 1;
        int currentDay = today.get(Calendar.DAY_OF_MONTH);

        int ageDifference = currentYear - dob.getYear();

        if (currentMonth < dob.getMonth() || (currentMonth == dob.getMonth()
                && currentDay < dob.getDay())) {
            ageDifference--;
        }

        return ageDifference < 24;
    }
}
