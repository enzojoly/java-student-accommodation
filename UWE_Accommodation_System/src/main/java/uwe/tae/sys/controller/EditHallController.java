package uwe.tae.sys.controller;

import uwe.tae.sys.model.Hall;
import uwe.tae.sys.model.Accommodation;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;

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
    private void initialize() {

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
