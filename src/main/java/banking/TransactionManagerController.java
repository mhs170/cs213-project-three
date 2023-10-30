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
            Double amount = Double.parseDouble(amountField.getText());

            //create date and check if valid
            Date dob = createDate(dobString);
            if(!dateIsValid(dob, dobString)) return;

            //run corresponding function
            switch (action) {
                case "Open" -> {}
                case "Close" -> {}
                case "Deposit" -> {}
                case "Withdraw" -> {}
            }
        }
        catch(NumberFormatException exp) {
            print(output, "Not a valid amount.");
        }
    }

    public void print(TextArea output, String string ) {
        output.appendText(string + "\n");
    }

    public void printFormatted(TextArea output, String formattedString, Object ... args) {
        String outputString = String.format(formattedString, args) + "\n";
        output.appendText(outputString);
    }

    //** methods for handling creation, deletion, update **//

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


}
