package uwe.tae.sys.controller;

import uwe.tae.sys.model.Hall;
import uwe.tae.sys.model.Accommodation;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;

public class EditHallController {

    Hall originalHall;

    Accommodation originalAccommodation;

    Hall hall;

    Accommodation accommodation;

    InformationUpdateCallback updateCallback;

    @FXML
    private TextFlow displaySelectedHall;

    @FXML
    private TextField insertNewHallManagerName;

    @FXML
    private TextField insertNewHallManagerTelephone;

    @FXML
    private TextField insertNewHallManagerID;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;


    @FXML
    private HBox enterHallManagerNameError, enterHallManagerTelephoneError, enterHallManagerIDError;

    // Initialize method
    @FXML
    private void initialize() {
        setupValidation();
    }

    private void setupValidation() {
	    // Validate the manager's name: first and last name separated by a space
	    validateTextField(insertNewHallManagerName, "^[A-Z][a-z]*+\\s[A-Z][a-z]*(?:-[A-Z][a-z]*)*$", "Please enter a valid name.", enterHallManagerNameError);
	    // Validate the telephone number: starts with "07" and is 11 digits long
	    validateTextField(insertNewHallManagerTelephone, "^07\\d{9}$", "Invalid phone number. Must be 11 digits long, starting '07'", enterHallManagerTelephoneError);
	    // Validate the manager ID: a 6-digit integer
	    validateTextField(insertNewHallManagerID, "^\\d{6}$", "Invalid ID. Must be 6 digits long", enterHallManagerIDError);
    }

    private void validateTextField(TextField textField, String pattern, String errorMessage, HBox container) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean matchesPattern = newValue.matches(pattern);
            if (!matchesPattern && !newValue.isEmpty()) {
                if (textField.getStyleClass().indexOf("text-field-error") == -1) {
                    textField.getStyleClass().add("text-field-error");
                }
                setErrorText(container, errorMessage);
            } else {
                textField.getStyleClass().remove("text-field-error");
                setErrorText(container, null);
            }
            checkFormValidity();
        });
    }

    private void setErrorText(HBox container, String errorMessage) {
        container.getChildren().removeIf(node -> node instanceof Text);
        if (errorMessage != null) {
            Text errorText = new Text(errorMessage);
            errorText.getStyleClass().add("error-text");
            container.getChildren().add(errorText);
        }
    }


    private void checkFormValidity() {
	    boolean isNameValid = insertNewHallManagerName.getText().isEmpty() || insertNewHallManagerName.getText().matches("^[A-Z][a-z]*+\\s[A-Z][a-z]*(?:-[A-Z][a-z]*)*$");
	    boolean isTelephoneValid = insertNewHallManagerTelephone.getText().isEmpty() || insertNewHallManagerTelephone.getText().matches("^07\\d{9}$"); // Corrected regex
	    boolean isIDValid = insertNewHallManagerID.getText().isEmpty() || insertNewHallManagerID.getText().matches("^\\d{6}$"); // Corrected regex for a 6-digit ID

	    // The confirm button should be enabled if all fields are valid (or empty for flexibility)
	    confirmButton.setDisable(!(isNameValid && isTelephoneValid && isIDValid)); // Simplified condition
    }

    public void handleConfirmAction() {
	updateHallManagerName();
	updateHallManagerTelephone();
    	updateHallManagerID();

	if (isAnyFieldFilled()) {
        	if (updateCallback != null) {
            		updateCallback.onInformationUpdated(hall, accommodation);
        	}
        closeWindow();
	} else { System.out.println("At least one field must be filled.");

    }
}

    private boolean isAnyFieldFilled() {
	return 	!insertNewHallManagerName.getText().isEmpty() ||
        	!insertNewHallManagerTelephone.getText().isEmpty() ||
		!insertNewHallManagerID.getText().isEmpty();
}
   @FXML
    public void handleCancelAction() {
        // Revert any changes and close the window
        hall = originalHall;
        accommodation = originalAccommodation;
	updateCallback.onInformationUpdated(hall, accommodation);
        cancelWindow();
    }

// TO DO: 	Input Validation logic for each field
//		individual fxml files for each detail to edit individually

    private void updateHallManagerName() {
	    if (!insertNewHallManagerName.getText().isEmpty())
	hall.getManager().setName(insertNewHallManagerName.getText());
	}

    private void updateHallManagerTelephone() {
	    if (!insertNewHallManagerTelephone.getText().isEmpty())
	hall.getManager().setTelephone(insertNewHallManagerTelephone.getText());
    }

    private void updateHallManagerID() {
	    if (!insertNewHallManagerID.getText().isEmpty())
	hall.getManager().setID(insertNewHallManagerID.getText());
    }

    private void closeWindow() {
	Stage stage = (Stage) confirmButton.getScene().getWindow();
	stage.close();
    }

    private void cancelWindow() {
	Stage stage = (Stage) cancelButton.getScene().getWindow();
	stage.close();
    }

    public void passVariables(Hall hall, Accommodation accommodation) {
	this.hall = hall;
	this.accommodation = accommodation;
	originalHall = hall; // create a deep copy of hall
        originalAccommodation = accommodation; // create a deep copy of accommodation
    }

    public void setUpdateCallback(InformationUpdateCallback updateCallback) {
        this.updateCallback = updateCallback;
    }



}
