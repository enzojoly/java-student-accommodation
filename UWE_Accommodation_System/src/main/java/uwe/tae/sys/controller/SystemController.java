package uwe.tae.sys.controller;

import uwe.tae.sys.model.UWE_Accommodation_System;
import uwe.tae.sys.model.Hall;
import uwe.tae.sys.model.Accommodation;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import java.io.IOException;

public class SystemController implements InformationUpdateCallback {

    private UWE_Accommodation_System uweSystem;

    private Hall selectedHall;

    private Accommodation selectedAccommodation;

    @FXML
    private ListView<String> displayInventoryList;

    @FXML
    private MenuButton selectBlock;

    @FXML
    private MenuItem selectStudentVillage;

    @FXML
    private MenuButton selectHall;

    @FXML
    private MenuItem selectHallBrecon;

    @FXML
    private MenuItem selectHallCotswold;

    @FXML
    private MenuItem selectHallMendip;

    @FXML
    private MenuItem selectHallQuantock;

    @FXML
    private TextFlow displayHallManagerName;

    @FXML
    private TextFlow displayHallManagerTelephone;

    @FXML
    private TextFlow displayHallManagerID;

    @FXML
    private ListView<String> associatedAccommodationsListSelect;

    @FXML
    private TextFlow displayAvailabilityStatus;

    @FXML
    private TextFlow displayCleaningStatus;

    @FXML
    private TextFlow displayAccommodationType;

    @FXML
    private TextFlow displayPrice;

    @FXML
    private TextFlow displayDescription;

    @FXML
    private TextFlow displayStudentName;

    @FXML
    private TextFlow displayStudentID;

    @FXML
    private TextFlow displayStudentTelephone;

    @FXML
    private TextFlow displayAssociatedLeaseNumber;

    @FXML
    private Button editDetailsButton;

    @FXML
    private Button createLeaseButton;

    @FXML
    private Button deleteLeaseButton;

    @FXML
    private void initialize() {

	uweSystem = UWE_Accommodation_System.createSystem();

        selectHall.setDisable(true);
	editDetailsButton.setDisable(true);
	createLeaseButton.setDisable(true);
	deleteLeaseButton.setDisable(true);

	setupEventHandlers();

    }

    private void setupEventHandlers() {

	selectStudentVillage.setOnAction(e -> handleSelectBlock());
	selectHallBrecon.setOnAction(e -> handleSelectHall(uweSystem.getStudentVillage().getHall("Brecon")));
	selectHallCotswold.setOnAction(e -> handleSelectHall(uweSystem.getStudentVillage().getHall("Cotswold")));
	selectHallMendip.setOnAction(e -> handleSelectHall(uweSystem.getStudentVillage().getHall("Mendip")));
	selectHallQuantock.setOnAction(e -> handleSelectHall(uweSystem.getStudentVillage().getHall("Quantock")));

	associatedAccommodationsListSelect.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {handleSelectAccommodation(newValue);
	});

    }


    private void handleSelectBlock() {

	List<Hall> halls = uweSystem.getStudentVillage().getHalls();

	selectHall.getItems().clear();

	for (Hall hall : halls) {
	        MenuItem hallItem = new MenuItem(hall.getName());
	        hallItem.setOnAction(e -> handleSelectHall(hall));
	        selectHall.getItems().add(hallItem);
	}

		selectHall.setDisable(false);
    }

    private void handleSelectHall(Hall hall) {
	setTextFlowContent(displayHallManagerName, hall.getManager().getName());
	setTextFlowContent(displayHallManagerTelephone, hall.getManager().getTelephone());
	setTextFlowContent(displayHallManagerID, String.valueOf(hall.getManager().getID()));
	selectedHall = hall;
	updateAccommodationsList(hall);

	editDetailsButton.setDisable(true);
    }

    private void updateAccommodationsList(Hall hall) {
	associatedAccommodationsListSelect.getItems().clear();
	for (Accommodation accommodation : hall.getAssociatedAccommodations()) {
		String displayText = hall.getName() + ", " + accommodation.getID() + ", " + accommodation.getType();
		associatedAccommodationsListSelect.getItems().add(displayText);
	}
    }

    private void refreshDetails(Hall hall, Accommodation accommodation) {
	setTextFlowContent(displayHallManagerName, hall.getManager().getName());
	setTextFlowContent(displayHallManagerTelephone, hall.getManager().getTelephone());
	setTextFlowContent(displayHallManagerID, String.valueOf(hall.getManager().getID()));
	setTextFlowContent(displayAvailabilityStatus, (accommodation.getAvailabilityStatus().toString()));
	setTextFlowContent(displayCleaningStatus, (accommodation.getCleaningStatus().toString()));
	setTextFlowContent(displayAccommodationType, (accommodation.getType().toString()));
        setTextFlowContent(displayPrice, accommodation.getPrice());
			   displayInventoryList.setItems(FXCollections.observableArrayList(accommodation.getInventory()));
	setTextFlowContent(displayDescription, accommodation.getDescription());

	// If the accommodation is not occupied, Write "N/A" in the student details fields
	if (accommodation.getRentalAgreement() == null) {
		setTextFlowContent(displayStudentName, "N/A");
		setTextFlowContent(displayStudentID, "N/A");
		setTextFlowContent(displayStudentTelephone, "N/A");
		setTextFlowContent(displayAssociatedLeaseNumber, "N/A");
		return;
	} else {
		setTextFlowContent(displayStudentName, accommodation.getRentalAgreement().getStudent().getName());
		setTextFlowContent(displayStudentID, accommodation.getRentalAgreement().getStudent().getID());
		setTextFlowContent(displayStudentTelephone, accommodation.getRentalAgreement().getStudent().getTelephone());
		setTextFlowContent(displayAssociatedLeaseNumber, accommodation.getRentalAgreement().getLeaseNumber());
	}
    }

    private void handleSelectAccommodation(String accommodationString) {

    	if (accommodationString == null) {
  		clearAccommodationDetails();

		selectedAccommodation = null;
  		return;
 	}

	String[] parts = accommodationString.split(" ");
	int accommodationID = Integer.parseInt(parts[1].replace(",", ""));
    	selectedAccommodation = findAccommodationById(accommodationID);

	editDetailsButton.setDisable(false);


    	refreshDetails(selectedHall, selectedAccommodation);

    }

    private Accommodation findAccommodationById(int id) {
	for (Hall hall : uweSystem.getStudentVillage().getHalls()) {
        	for (Accommodation accommodation : hall.getAssociatedAccommodations()) {
        		if (accommodation.getID() == id) {
        			return accommodation;
	            	}
	        }
	}
	return null;
    }

    private void clearAccommodationDetails() {
	// Clear or set default content for all TextFlows
	setTextFlowContent(displayAvailabilityStatus, "");
	setTextFlowContent(displayCleaningStatus, "");
	setTextFlowContent(displayAccommodationType, "");
	setTextFlowContent(displayPrice, "");
	setTextFlowContent(displayDescription, "");
	// Clear other fields as well
    }

    @FXML
    private void handleEditDetailsAction() {
    	onEditDetailsClicked(selectedAccommodation);
    }

    @FXML
    private void onEditDetailsClicked(Accommodation accommodation) {
	    if (selectedAccommodation != null) {
        	openEditWindow("/uwe/tae/sys/view/edit.fxml", "Edit Details", selectedHall, selectedAccommodation);
	    }
    }

    @FXML
    private void onCreateLeaseClicked() {
	if (selectedAccommodation != null) {
        	openLeaseWindow("/uwe/tae/sys/view/lease.fxml", "Create Lease", selectedHall, selectedAccommodation);
	}
    }

    @FXML
    private void onDeleteLeaseClicked() {
	if (selectedAccommodation.getRentalAgreement() != null) {
		selectedAccommodation.deleteRentalAgreement();
		refreshDetails(selectedHall, selectedAccommodation);
	} else { System.out.println("No lease to delete"); }
    }

    private void setTextFlowContent(TextFlow textFlow, String content) {
        textFlow.getChildren().clear();
        textFlow.getChildren().add(new Text(content));
    }

    private void openLeaseWindow(String fxmlFile, String title, Hall selectedHall, Accommodation selectedAccommodation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane pane = loader.load();

	    LeaseController leaseController = loader.getController();
	    leaseController.passVariables(selectedHall, selectedAccommodation);
	    leaseController.setUpdateCallback(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new Scene(pane));
            stage.showAndWait();
	    refreshDetails(selectedHall, selectedAccommodation);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void openEditWindow(String fxmlFile, String title, Hall selectedHall, Accommodation selectedAccommodation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane pane = loader.load();

	    EditController editController = loader.getController();
	    editController.passVariables(selectedHall, selectedAccommodation);
	    editController.setUpdateCallback(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(new Scene(pane));
            stage.showAndWait();
	    refreshDetails(selectedHall, selectedAccommodation);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onInformationUpdated(Hall hall, Accommodation accommodation) {
        // Update objects and refresh UI
	this.selectedHall = hall;
	this.selectedAccommodation = accommodation;
        refreshDetails(selectedHall, selectedAccommodation);
        System.out.println("Callback triggered with accommodation ID: " + accommodation.getID());
    }

}
