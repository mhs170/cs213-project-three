package banking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

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

    public void onActionSelect(ActionEvent event) {
        String action = actionPicker.getValue();

        //clear the form
        clear(event);

        //change the ui to match which action is selected
        //make fields visible or invisible
        switch(action) {
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

    public void onAccountSelect(ActionEvent event) {
        String selectedType = accountTypePicker.getValue();

        //enable campus picker for a college checking account
        if(selectedType.equals("College Checking")) {
            campusPicker.setDisable(false);
        }
        else {
            campusPicker.setDisable(true);
            campusPicker.setValue("New Brunswick");
        }

        //enable isLoyal checkbox for a savings account
        if(selectedType.equals("Savings")) {
            loyaltyCheckbox.setDisable(false);
        }
        else {
            loyaltyCheckbox.setDisable(true);
            loyaltyCheckbox.setSelected(false);
        }
    }

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
            if(firstName.isEmpty()) {
                print(output, "Please enter a first name");
                return;
            }
            if(lastName.isEmpty()) {
                print(output, "Please enter a last name");
                return;
            }

            //create date and check if valid
            if(dobString.isEmpty()) {
                print(output, "Please enter a date of birth.");
                return;
            }
            Date dob = createDate(dobString);
            if(!dateIsValid(dob, dobString)) return;

            //parse amount (throws exception)
            if(amountStr.isEmpty()) {
                print(output, "Please enter a valid amount.");
                return;
            }
            Double amount = Double.parseDouble(amountStr);

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
                case "Close" -> {}
                case "Deposit" -> {}
                case "Withdraw" -> {}
            }
        }
        catch(NumberFormatException exp) {
            print(output, "Not a valid amount.");
        }
    }

    //*** helper methods ***//

    /**
     * Print out a string by appending it to a TEXT AREA
     * @param output the TextArea to be appended to
     * @param string the string to be printed
     */
    public void print(TextArea output, String string ) {
        output.appendText(string + "\n");
    }

    /**
     * Print out a string by appending it to a TEXT AREA
     * @param output the TextArea to be appended to
     * @param formatString the string to be formatted and printed
     * @param args the arguments used to format the string
     */
    public void printFormatted(TextArea output, String formatString, Object ... args) {
        String outputString = String.format(formatString, args) + "\n";
        output.appendText(outputString);
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
                accountType,
                status);
    }

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
            double initialDeposit
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
     * @param campus     string of campus
     */
    private void OpenCollegeCheckingAccount(
            String firstName,
            String lastName,
            Date dateOfBirth,
            double initialDeposit,
            String campus
    ) {
        if (dateOfBirth.getYearsSince() > 24){
            print(output, "DOB invalid: " + dateOfBirth + " over 24.");
            return;
        }
        Campus campusObj;
        switch (campus) {
            case "New Brunswick" -> campusObj = Campus.NEW_BRUNSWICK;
            case "Newark" -> campusObj = Campus.NEWARK;
            case "Camden" -> campusObj = Campus.CAMDEN;
            default -> {
                //unlikely to happen
                print(output, "Invalid campus.");
                return;
            }
        }
        Profile profile = new Profile(firstName, lastName, dateOfBirth);
        CollegeChecking account = new CollegeChecking(profile,
                initialDeposit, campusObj);
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
     * @param isLoyal boolean based on whether customer is loyal
     */
    private void OpenSavingsAccount(
            String firstName,
            String lastName,
            Date dateOfBirth,
            double initialDeposit,
            boolean isLoyal
    ) {
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
            double initialDeposit
    ) {
        if (initialDeposit < 2000.00) {
            print(output,
                    "Minimum of $2000 to open a Money Market account."
            );
            return;
        }
        Profile profile = new Profile(firstName, lastName, dateOfBirth);
        MoneyMarket account = new MoneyMarket(profile, initialDeposit,
                true, 0);
        boolean openSuccess = database.open(account);
        printOpenStatus(profile, "MM", openSuccess);
    }

    /**
     * Creates an account
     * @param firstName string first name
     * @param lastName string last name
     * @param dob date of birth
     * @param accountType type of account
     * @param campus string campus
     * @param isLoyal boolean is loyal
     * @param initialDeposit initial deposit as double
     */
    private void OpenAccount(
            String firstName,
            String lastName,
            Date dob,
            String accountType,
            String campus,
            boolean isLoyal,
            Double initialDeposit
    ) {
            if (dob.getYearsSince() < 16){
                print(output, "DOB invalid: " + dob + " under 16.");
                return;
            }

            if (initialDeposit <= 0) {
                print(output,"Initial deposit cannot be 0 or negative" +
                        ".");
                return;
            }

            switch (accountType) {
                case "Checking" -> OpenCheckingAccount(
                        firstName, lastName, dob, initialDeposit
                );
                case "College Checking" -> OpenCollegeCheckingAccount(
                        firstName, lastName, dob, initialDeposit, campus
                );
                case "Savings" -> OpenSavingsAccount(
                        firstName, lastName, dob, initialDeposit, isLoyal
                );
                case "Money Market" -> OpenMoneyMarketAccount(
                        firstName, lastName, dob, initialDeposit
                );
            }
    }


}
