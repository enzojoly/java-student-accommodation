package uwe.tae.sys.controller;

import uwe.tae.sys.model.Hall;
import uwe.tae.sys.model.Accommodation;
import uwe.tae.sys.model.AccommodationType;
import uwe.tae.sys.model.CleaningStatus;

import java.util.ArrayList;
import java.util.Arrays;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.layout.HBox;

public class EditController {

    Hall originalHall;

    Accommodation originalAccommodation;

    //private String defaultInventory = String.join(", ", accommodation.getDefaultInventoryForType(accommodation.getType()));

    //private String defaultDescription = accommodation.getDefaultDescriptionForType(accommodation.getType());

    private AccommodationType originalType;

    private String originalDescription;

    private String originalInventory;

    private CleaningStatus originalCleaningStatus;

    Hall hall;

    Accommodation accommodation;

    InformationUpdateCallback updateCallback;

    @FXML
    private HBox EnterPriceError, EnterInventoryError, EnterDescriptionError;

    @FXML
    private TextFlow displaySelectedHall, displayAccommodationNumber;

    @FXML
    private TextField insertNewPrice, insertNewInventory, insertNewDescription;

    @FXML
    private MenuButton setType, setStatus;

    @FXML
    private MenuItem selectStandard, selectSuperior, selectDirty, selectOffline, selectClean;

    @FXML
    private Button setDefaultInventory, setDefaultDescription, cancelButton, confirmButton;

    @FXML
    private void initialize() {
	setupEventHandlers();
	setupValidation();
	confirmButton.setDisable(true);
    }

    private void setupValidation() {
	    validateTextField(insertNewPrice, "^\\d+(?:\\.\\d{1,2})?$", "Invalid price entry", EnterPriceError);
            validateTextField(insertNewInventory, "a", "Inventory not standard. Status will be set to OFFLINE", EnterInventoryError);
	    validateTextField(insertNewDescription, "a", "Description diverges from the norm", EnterDescriptionError);
    }

    private void validateTextField(TextField textField, String pattern, String errorMessage, HBox errorContainer) {
	    textField.textProperty().addListener((observable, oldValue, newValue) -> {
		    boolean matchesPattern = newValue.matches(pattern);
		    if (!matchesPattern && !newValue.isEmpty()) {
			    if (textField.getStyleClass().indexOf("text-field-error") == -1) {
				    textField.getStyleClass().add("text-field-error");
			    }
			    setErrorText(errorContainer, errorMessage);
		    } else {
			    textField.getStyleClass().remove("text-field-error");
			    setErrorText(errorContainer, null);
		    }
		    updateConfirmButtonState();
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

    private void updateConfirmButtonState() {
	    confirmButton.setDisable(!(isAnyFieldFilled() && isPriceNotInvalid()));
    }

    private boolean isPriceNotInvalid() {
	    // Check if the price field is not invalid (it is either empty or matches the pattern)
	    String price = insertNewPrice.getText();
	    return price.isEmpty() || price.matches("^\\d+(?:\\.\\d{1,2})?$");
    }

    private void setupEventHandlers() {

	selectStandard.setOnAction(e -> handleSetType(AccommodationType.STANDARD));
	selectSuperior.setOnAction(e -> handleSetType(AccommodationType.SUPERIOR));
	selectClean.setOnAction(e -> handleSetCleaningStatus(CleaningStatus.CLEAN));
	selectDirty.setOnAction(e -> handleSetCleaningStatus(CleaningStatus.DIRTY));
	selectOffline.setOnAction(e -> handleSetCleaningStatus(CleaningStatus.OFFLINE));
    }

    String typeActive;
    String cleaningActive;

    public void handleSetType(AccommodationType type) {
	accommodation.setType(type);
	accommodation.setDefaultsBasedOnType(type);
	typeActive = "type changed";
	updateConfirmButtonState();
    }


    public void handleSetCleaningStatus(CleaningStatus cleaningStatus) {
	accommodation.setCleaningStatus(cleaningStatus);
	cleaningActive = "status changed";
	updateConfirmButtonState();
    }


    @FXML
    public void handleConfirmAction() {
    	updatePrice();
    	updateInventory();
    	updateDescription();

	if (isAnyFieldFilled()) {
        	if (updateCallback != null) {
            		updateCallback.onInformationUpdated(hall, accommodation);
        	}
        closeWindow();
	} else { System.out.println("At least one field must be filled.");

    }
}

    private boolean isAnyFieldFilled() {
	return	!insertNewPrice.getText().isEmpty() ||
           	!insertNewInventory.getText().isEmpty() ||
           	!insertNewDescription.getText().isEmpty() ||
		typeActive != null ||
		cleaningActive != null;
	}

    @FXML
    public void handleCancelAction() {
        // Revert any changes and close the window
	accommodation.setType(originalType);
	accommodation.setDescription(originalDescription);
	accommodation.setInventory(new ArrayList<>(Arrays.asList(originalInventory.split("\\s*,\\s*"))));
	accommodation.setCleaningStatus(originalCleaningStatus);
	updateCallback.onInformationUpdated(hall, accommodation);
        cancelWindow();
    }

// TO DO: 	Input Validation logic for each field
//		individual fxml files for each detail to edit individually

    private void updatePrice() {
	    if (!insertNewPrice.getText().isEmpty())
	accommodation.setPrice(Double.parseDouble(insertNewPrice.getText()));
    }


    private void updateInventory() {
	if (!insertNewInventory.getText().isEmpty()) {
	// Directly parse the text from insertNewInventory into a list
	accommodation.setInventory(new ArrayList<>(Arrays.asList(insertNewInventory.getText().split("\\s*,\\s*"))));
	}
    }

    private void updateDescription() {
	if (!insertNewDescription.getText().isEmpty())
	accommodation.setDescription(insertNewDescription.getText());
    }


    private void setTextFlowContent(TextFlow textFlow, String content) {
        textFlow.getChildren().clear();
        textFlow.getChildren().add(new Text(content));
    }

    @FXML
    private void handleDefaultInventory() {
	    insertNewInventory.setText(originalAccommodation.defaultInventoryString());
    }

    @FXML
    private void handleDefaultDescription() {
	    insertNewDescription.setText(originalAccommodation.defaultDescriptionString());
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
	this.originalHall = hall; // create a deep copy of hall
	this.originalAccommodation = accommodation; // create a deep copy of accommodation
	this.originalDescription = accommodation.getDescription();
	this.originalInventory = String.join(", ", accommodation.getInventory());
	this.originalType = accommodation.getType();
	this.originalCleaningStatus = accommodation.getCleaningStatus();

        	setTextFlowContent(displayAccommodationNumber, String.valueOf(accommodation.getID()));
		setTextFlowContent(displaySelectedHall, hall.getName());

    }

    public void setUpdateCallback(InformationUpdateCallback updateCallback) {
        this.updateCallback = updateCallback;
    }



}
