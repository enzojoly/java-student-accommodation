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

public class EditController {

    Hall originalHall;

    Accommodation originalAccommodation;

    Hall hall;

    Accommodation accommodation;

    InformationUpdateCallback updateCallback;

    @FXML
    private TextFlow displaySelectedHall;

    @FXML
    private TextFlow displayAccommodationNumber;

    @FXML
    private TextField insertNewHallManagerName;

    @FXML
    private TextField insertNewHallManagerTelephone;

    @FXML
    private TextField insertNewHallManagerID;

    @FXML
    private TextField insertNewPrice;

    @FXML
    private MenuButton setType;

    @FXML
    private MenuItem selectStandard;

    @FXML
    private MenuItem selectSuperior;

    @FXML
    private MenuButton setStatus;

    @FXML
    private MenuItem selectClean;

    @FXML
    private MenuItem selectDirty;

    @FXML
    private MenuItem selectOffline;

    @FXML
    private Button setDefaultInventory;

    @FXML
    private Button setDefaultDescription;

    @FXML
    private TextField insertNewInventory;

    @FXML
    private TextField insertNewDescription;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @FXML
    private void initialize() {
	    setupEventHandlers();
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
    }


    public void handleSetCleaningStatus(CleaningStatus cleaningStatus) {
	accommodation.setCleaningStatus(cleaningStatus);
	cleaningActive = "status changed";
    }


    @FXML
    public void handleConfirmAction() {
	updateHallManagerName();
	updateHallManagerTelephone();
    	updateHallManagerID();
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
	return 	!insertNewHallManagerName.getText().isEmpty() ||
        	!insertNewHallManagerTelephone.getText().isEmpty() ||
		!insertNewHallManagerID.getText().isEmpty() ||
           	!insertNewPrice.getText().isEmpty() ||
           	!insertNewInventory.getText().isEmpty() ||
           	!insertNewDescription.getText().isEmpty() ||
		typeActive != null ||
		cleaningActive != null;
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
	originalHall = hall; // create a deep copy of hall
        originalAccommodation = accommodation; // create a deep copy of accommodation

	if (accommodation != null) {
        	setTextFlowContent(displayAccommodationNumber, String.valueOf(accommodation.getID()));
		setTextFlowContent(displaySelectedHall, hall.getName());
	}
    }

    public void setUpdateCallback(InformationUpdateCallback updateCallback) {
        this.updateCallback = updateCallback;
    }



}
