package uwe.tae.sys.controller;

import uwe.tae.sys.model.Hall;
import uwe.tae.sys.model.Accommodation;
import uwe.tae.sys.model.RentalAgreement;
import uwe.tae.sys.model.Student;

import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class LeaseController {

    Hall hall;
    Accommodation accommodation;
    Student student; RentalAgreement rentalAgreement;
    InformationUpdateCallback updateCallback;

    @FXML
    private TextFlow displaySelectedHall;

    @FXML
    private TextFlow displayAccommodationNumber;

    @FXML
    private TextField enterStudentName;

    @FXML
    private TextField enterStudentID;

    @FXML
    private TextField enterStudentTelephone;

    @FXML
    private TextFlow displayPrice;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    // Initialize method
    @FXML
    private void initialize() {
    }

    @FXML
    public void handleConfirmAction() {
//Testing
//	System.out.println("enterStudentName: " + (enterStudentName != null ? "Initialized" : "Not Initialized"));
//	System.out.println("enterStudentID: " + (enterStudentID != null ? "Initialized" : "Not Initialized"));
//	System.out.println("enterStudentTelephone: " + (enterStudentTelephone != null ? "Initialized" : "Not Initialized"));

	if (enterStudentName == null || enterStudentID == null || enterStudentTelephone == null) {
            System.out.println("One or more fields are not initialized.");
            return;
	}


//	if (enterStudentName.getText().isEmpty() || enterStudentID.getText().isEmpty() || enterStudentTelephone.getText().isEmpty()) {
//            System.out.println("Please fill all fields.");
//            return;
//        }

        String studentName = enterStudentName.getText();
        String studentTelephone = enterStudentTelephone.getText();
        int studentIDInt = Integer.parseInt(enterStudentID.getText());

        RentalAgreement rentalAgreement = createLease(studentName, studentIDInt, studentTelephone);
        accommodation.setRentalAgreement(rentalAgreement);

        if (updateCallback != null) {
            updateCallback.onInformationUpdated(hall, accommodation);
        }
        closeWindow();
    }



    private RentalAgreement createLease(String studentName, int studentIDInt, String studentTelephone) {

	Student student = new Student(studentName, studentTelephone, studentIDInt);
	return new RentalAgreement(student);
    }


    @FXML
    public void handleCancelAction() {
	if (enterStudentName != null) {
        	enterStudentName.clear();
	} else if (enterStudentID != null) {
		enterStudentID.clear();
	} else if (enterStudentTelephone != null) {
        	enterStudentTelephone.clear();
	} else {
	}	cancelWindow();
    }


    private void closeWindow() {
	Stage stage = (Stage) confirmButton.getScene().getWindow();
	stage.close();
    }

    private void cancelWindow() {
	Stage stage = (Stage) cancelButton.getScene().getWindow();
	stage.close();
    }

    // Utility method to set text in a TextFlow
    private void setTextFlowContent(TextFlow textFlow, String content) {
        textFlow.getChildren().clear();
        textFlow.getChildren().add(new Text(content));
    }

    public void passVariables(Hall hall, Accommodation accommodation) {
	this.hall = hall;
	this.accommodation = accommodation;

	if (accommodation != null) {
        	setTextFlowContent(displayAccommodationNumber, String.valueOf(accommodation.getID()));
        	setTextFlowContent(displayPrice, accommodation.getPrice());
		setTextFlowContent(displaySelectedHall, hall.getName());
	}
    }

    public void setUpdateCallback(InformationUpdateCallback updateCallback) {
        this.updateCallback = updateCallback;
    }

}
