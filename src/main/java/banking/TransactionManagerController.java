package banking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TransactionManagerController {

    @FXML
    private ChoiceBox<String> accountTypePicker1;

    @FXML
    private ChoiceBox<String> campusPicker;

    @FXML
    private DatePicker dobPicker1;

    @FXML
    private TextField firstNameField1;

    @FXML
    private TextField lastNameField1;

    @FXML
    private CheckBox loyaltyCheckbox;

    @FXML
    private Button openButton;

    @FXML
    public void initialize() {

        String[] accountTypes = {
                "Checking", "College Checking", "Savings", "Money Market"
        };

        String[] campusTypes = {
                "New Brunswick", "Newark", "Camden"
        };

        //initialize account type dropdown
        accountTypePicker1.getItems().addAll(accountTypes);
        //default value
        accountTypePicker1.setValue("Checking");
        //register handler manually
        accountTypePicker1.setOnAction(this::onAccountSelect);

        //initialize campus dropdown
        campusPicker.getItems().addAll(campusTypes);
        campusPicker.setValue("New Brunswick");

    }

   public void onAccountSelect(ActionEvent event) {
        String selectedType = accountTypePicker1.getValue();

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
    void openAccount(ActionEvent event) {

    }
}
