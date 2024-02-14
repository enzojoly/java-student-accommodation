package uwe.tae.sys.controller;

import uwe.tae.sys.model.UWE_Accommodation_System;
import uwe.tae.sys.model.Hall;
import uwe.tae.sys.model.Accommodation;
import uwe.tae.sys.model.AvailabilityStatus;
import uwe.tae.sys.model.CleaningStatus;

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

    private int totalRooms;

    private int availableRooms;

    private int offlineRooms;

    private int requireCleaning;

    @FXML
    private ListView<String> displayInventoryList;

    @FXML
    private TextFlow defaultMatchInfo;

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
    private TextFlow displayTotalRooms;

    @FXML
    private TextFlow displayAvailableRooms;

    @FXML
    private TextFlow displayOfflineRooms;

    @FXML
    private TextFlow displayRequireCleaning;

    @FXML
    private ListView<String> associatedAccommodationsListSelect;

    @FXML
    private TextFlow displayAccommodationNumber;

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
    private Button editHallDetailsButton, editDetailsButton, createLeaseButton, deleteLeaseButton;

    @FXML
    private void initialize() {

	uweSystem = UWE_Accommodation_System.getSystem();

        selectHall.setDisable(true);
	editHallDetailsButton.setDisable(true);
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
	editHallDetailsButton.setDisable(false);
	editDetailsButton.setDisable(true);
	createLeaseButton.setDisable(true);
	deleteLeaseButton.setDisable(true);




	//Null pointer exception for selectedAccommodation as on first boot refreshDetails is called before selectedAccommodation is set
	try {
		refreshDetails(selectedHall, selectedAccommodation);
	} catch (NullPointerException e) {
		System.out.println("Refreshed details. No accommodation selected");
	}
    }

    private void updateAccommodationsList(Hall hall) {
	associatedAccommodationsListSelect.getItems().clear();

	totalRooms = 0;
	availableRooms = 0;
	offlineRooms = 0;
	requireCleaning = 0;

	for (Accommodation accommodation : hall.getAssociatedAccommodations()) {

		// I call this monster the String maker, also it provides total variables for header display

		totalRooms++;

		String displayText = hall.getName() + ", " + accommodation.getID() + ", " + accommodation.getType();

		if (accommodation.getRentalAgreement() == null) {
		if (accommodation.getCleaningStatus() == CleaningStatus.CLEAN) {
			availableRooms++;
			associatedAccommodationsListSelect.getItems().add(displayText);
		} else if (accommodation.getCleaningStatus() == CleaningStatus.OFFLINE) {
			offlineRooms++;
			associatedAccommodationsListSelect.getItems().add(displayText + ", OFFLINE");
		} else if (accommodation.getCleaningStatus() == CleaningStatus.DIRTY) {
			requireCleaning++;
			associatedAccommodationsListSelect.getItems().add(displayText + ", REQUIRES CLEANING");
		}
		} else if (accommodation.getRentalAgreement() != null) {
			if (accommodation.getCleaningStatus() == CleaningStatus.CLEAN) {
			associatedAccommodationsListSelect.getItems().add(displayText + ", Leased to "
					+ accommodation.getRentalAgreement().getStudent().getID());

		} else if (accommodation.getCleaningStatus() == CleaningStatus.OFFLINE) {
			offlineRooms++;
			associatedAccommodationsListSelect.getItems().add(displayText + ", Leased to "
					+ accommodation.getRentalAgreement().getStudent().getID() + ", OFFLINE");
		// Can a student occupy an offline room? Only if Offline triggered after occupation, apparently.
		// Do we care if description doesn't match? It won't show on the list

		} else if (accommodation.getCleaningStatus() == CleaningStatus.DIRTY){
			requireCleaning++;
			associatedAccommodationsListSelect.getItems().add(displayText + ", Leased to "
					+ accommodation.getRentalAgreement().getStudent().getID() + ", REQUIRES CLEANING");
		}
	}
	}
	refreshHallSummary();
	}

    private void refreshHallSummary() {
	setTextFlowContent(displayTotalRooms, String.valueOf(totalRooms));
	setTextFlowContent(displayAvailableRooms, String.valueOf(availableRooms));
	setTextFlowContent(displayOfflineRooms, String.valueOf(offlineRooms));
	setTextFlowContent(displayRequireCleaning, String.valueOf(requireCleaning));
    }


    private void refreshDetails(Hall hall, Accommodation accommodation) {
	    updateAccommodationsList(hall);
	setTextFlowContent(displayHallManagerName, hall.getManager().getName());
	setTextFlowContent(displayHallManagerTelephone, hall.getManager().getTelephone());
	setTextFlowContent(displayHallManagerID, String.valueOf(hall.getManager().getID()));
	refreshHallSummary();
	if (accommodation != null) {
	setTextFlowContent(displayAccommodationNumber, String.valueOf(accommodation.getID()));
	setTextFlowContent(displayAvailabilityStatus, (accommodation.getAvailabilityStatus().toString()));
	setTextFlowContent(displayCleaningStatus, (accommodation.getCleaningStatus().toString()));
	setTextFlowContent(displayAccommodationType, (accommodation.getType().toString()));
        setTextFlowContent(displayPrice, accommodation.getPrice());
			   displayInventoryList.setItems(FXCollections.observableArrayList(accommodation.getInventory()));
	setTextFlowContent(displayDescription, accommodation.getDescription());
	printInventoryCode(accommodation);
        editDetailsButton.setDisable(true);
	createLeaseButton.setDisable(true);
	deleteLeaseButton.setDisable(true);

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
    }

    private void refreshOnSelect(Accommodation accommodation) {
	setTextFlowContent(displayAccommodationNumber, String.valueOf(accommodation.getID()));
	setTextFlowContent(displayAvailabilityStatus, (accommodation.getAvailabilityStatus().toString()));
	setTextFlowContent(displayCleaningStatus, (accommodation.getCleaningStatus().toString()));
	setTextFlowContent(displayAccommodationType, (accommodation.getType().toString()));
        setTextFlowContent(displayPrice, accommodation.getPrice());
			   displayInventoryList.setItems(FXCollections.observableArrayList(accommodation.getInventory()));
	setTextFlowContent(displayDescription, accommodation.getDescription());
	printInventoryCode(accommodation);


	// If the accommodation is not occupied, Write "N/A" in the student details fields
	if (accommodation.getRentalAgreement() == null) {
		setTextFlowContent(displayStudentName, "N/A");
		setTextFlowContent(displayStudentID, "N/A");
		setTextFlowContent(displayStudentTelephone, "N/A");
		setTextFlowContent(displayAssociatedLeaseNumber, "N/A");
		if (accommodation.getAvailabilityStatus() == AvailabilityStatus.AVAILABLE) {
		createLeaseButton.setDisable(false);
		}
		if (accommodation.getCleaningStatus() != CleaningStatus.CLEAN) {
		createLeaseButton.setDisable(true);
		}
		deleteLeaseButton.setDisable(true);
		return;
	} else {
		setTextFlowContent(displayStudentName, accommodation.getRentalAgreement().getStudent().getName());
		setTextFlowContent(displayStudentID, accommodation.getRentalAgreement().getStudent().getID());
		setTextFlowContent(displayStudentTelephone, accommodation.getRentalAgreement().getStudent().getTelephone());
		setTextFlowContent(displayAssociatedLeaseNumber, accommodation.getRentalAgreement().getLeaseNumber());
		createLeaseButton.setDisable(true);
		deleteLeaseButton.setDisable(false);
	}
    }


    private void printInventoryCode(Accommodation accommodation) {

	if (accommodation.inventoryMatchesDefault()) {
	if (accommodation.descriptionMatchesDefault()) {
		setTextFlowContent(defaultMatchInfo, "Inventory up to code");
	} else {
		setTextFlowContent(defaultMatchInfo, "Description diverges from the norm.");
	}
	} else {
		setTextFlowContent(defaultMatchInfo, "Inventory does not match requirements, OFFLINE");
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

	refreshOnSelect(selectedAccommodation);
	editDetailsButton.setDisable(false);

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
    	onEditDetailsClicked(selectedHall, selectedAccommodation);
    }

    @FXML
    private void handleEditHallDetailsAction() {
    	onEditHallDetailsClicked(selectedHall, selectedAccommodation);
    }

    @FXML
    private void onEditDetailsClicked(Hall hall, Accommodation accommodation) {
	    if (selectedAccommodation != null) {
        	openEditWindow("/uwe/tae/sys/view/edit.fxml", "Edit Accommodation Details", selectedHall, selectedAccommodation);
	    }
    }

    @FXML
    private void onEditHallDetailsClicked(Hall hall, Accommodation accommodation) {
	    if (hall != null) {
        	openEditHallWindow("/uwe/tae/sys/view/editHall.fxml", "Edit Hall Details", selectedHall, selectedAccommodation);
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
		createLeaseButton.setDisable(true);
		deleteLeaseButton.setDisable(true);
		editDetailsButton.setDisable(true);
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

            Scene scene = new Scene(pane);
            String css = getClass().getResource("/uwe/tae/sys/css/styles.css").toExternalForm();
            scene.getStylesheets().add(css); // Adding the CSS file to the scene

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.showAndWait();
	    refreshDetails(selectedHall, selectedAccommodation);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void openEditHallWindow(String fxmlFile, String title, Hall selectedHall, Accommodation selectedAccommodation) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane pane = loader.load();

	    EditHallController editHallController = loader.getController();
	    editHallController.passVariables(selectedHall, selectedAccommodation);
	    editHallController.setUpdateCallback(this);

	    Scene scene = new Scene(pane);
            String css = getClass().getResource("/uwe/tae/sys/css/styles.css").toExternalForm();
            scene.getStylesheets().add(css); // Adding the CSS file to the scene

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(scene);
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

	    Scene scene = new Scene(pane);
            String css = getClass().getResource("/uwe/tae/sys/css/styles.css").toExternalForm();
            scene.getStylesheets().add(css); // Adding the CSS file to the scene

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(title);
            stage.setScene(scene);
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
	if (accommodation != null) {
	this.selectedAccommodation = accommodation;
        System.out.println("Callback triggered with accommodation ID: " + accommodation.getID());
	} else {         System.out.println("Callback triggered with selected hall");
	}
        refreshDetails(selectedHall, selectedAccommodation);
    }

}
