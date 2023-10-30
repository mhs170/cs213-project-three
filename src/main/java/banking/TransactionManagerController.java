package banking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class TransactionManagerController {

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

        if(selectedType.equals("College Checking")) {
            campusPicker.setDisable(false);
        }
        else {
            campusPicker.setDisable(true);
            campusPicker.setValue("New Brunswick");
        }

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
        output.appendText("Submit button clicked\n");
    }


}
