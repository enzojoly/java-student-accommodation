package uwe.tae.sys.model;

import java.util.Arrays;
import java.util.List;


public class Accommodation {
    private static int nextAccommodationNumber = 101;
    private int accommodationNumber;
    private AccommodationType type;
    private double price;
    private CleaningStatus cleaningStatus;
    private List<String> inventory;
    private String description;
    private RentalAgreement rentalAgreement;

    public Accommodation(AccommodationType type) {
        this.accommodationNumber = getNextAccommodationNumber();
	this.type = type;
	this.cleaningStatus = CleaningStatus.CLEAN;
        setDefaultsBasedOnType(type);
    }

    private static synchronized int getNextAccommodationNumber() {
        return nextAccommodationNumber++;
    }

    public void setDefaultsBasedOnType(AccommodationType type) {
        switch (type) {
            case STANDARD:
                this.price = 700.00;
                this.description = "Standard Room with basic amenities";
                this.inventory = Arrays.asList("Single bed", "Wardrobe", "Desk", "Chair", "Bookshelves", "Bedside cabinet", "Mirror", "En-suite wet room");
                break;
            case SUPERIOR:
                this.price = 750.00;
                this.description = "Superior Room with advanced amenities";
                this.inventory = Arrays.asList("Single bed", "Wardrobe", "Large desk", "Chair", "Bookshelves", "Bedside cabinet", "Mirror", "Large En-suite wet room");
                break;
        }
    }

    public void setDefaultDescription() {
        this.description = getDefaultDescriptionForType(this.type);
    }

    private String getDefaultDescriptionForType(AccommodationType type) {
        switch (type) {
            case STANDARD:
                return "Standard Room with basic amenities";
            case SUPERIOR:
                return "Superior Room with advanced amenities";
            default:
                return "";
        }
    }

     private boolean inventoryMatchesDefault() {
        List<String> defaultInventory = getDefaultInventoryForType(this.type);
        return this.inventory.equals(defaultInventory);
    }

    private List<String> getDefaultInventoryForType(AccommodationType type) {
        switch (type) {
            case STANDARD:
                return Arrays.asList("Single bed", "Wardrobe", "Desk", "Chair", "Bookshelves", "Bedside cabinet", "Mirror", "En-suite wet room");
            case SUPERIOR:
                return Arrays.asList("Single bed", "Wardrobe", "Large desk", "Chair", "Bookshelves", "Bedside cabinet", "Mirror", "Large En-suite wet room");
            default:
                return Arrays.asList("No inventory");
        }
    }

    public void setDefaultInventory() {
	this.inventory = getDefaultInventoryForType(this.type);
    }

    public String defaultInventoryString() {
	List<String> defaultInventory = getDefaultInventoryForType(this.type);
	return String.join(", ", defaultInventory);
    }

    public String defaultDescriptionString() {
        return getDefaultDescriptionForType(this.type);
    }

    public int getID() {
        return accommodationNumber;
    }

    public void setID(int accommodationNumber) {
        this.accommodationNumber = accommodationNumber;
    }

    public AccommodationType getType() {
        return type;
    }

    public void setType(AccommodationType type) {
        this.type = type;
    }

    public String getPrice() {
	String priceString = "£" + String.format("%.2f", price);
	return priceString;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPrice(String price) {
	this.price = Double.parseDouble(price);
    }

    public AvailabilityStatus getAvailabilityStatus() {
	    if (this.rentalAgreement != null) {
		    return AvailabilityStatus.UNAVAILABLE;
	    } else if (this.cleaningStatus != CleaningStatus.CLEAN) {
		    return AvailabilityStatus.UNAVAILABLE;
	    } else if (!inventoryMatchesDefault()) {
		    return AvailabilityStatus.UNAVAILABLE;
	    } else {
		    return AvailabilityStatus.AVAILABLE;
	    }
    }

    public OccupancyStatus getOccupancyStatus() {
	    if (this.rentalAgreement != null) {
		    return OccupancyStatus.OCCUPIED;
	    } else {
		    return OccupancyStatus.UNOCCUPIED;
	    }
    }

    public CleaningStatus getCleaningStatus() {
        if ((this.cleaningStatus == CleaningStatus.CLEAN && !inventoryMatchesDefault()) || this.cleaningStatus == CleaningStatus.OFFLINE) {
		 return CleaningStatus.OFFLINE;
        } else if (this.cleaningStatus == CleaningStatus.DIRTY) {
		return CleaningStatus.DIRTY;
	} else {
		return CleaningStatus.CLEAN;
	}
    }

    public void setCleaningStatus(CleaningStatus cleaningStatus) {
	    this.cleaningStatus = cleaningStatus;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public void setInventory(List<String> inventory) {
        this.inventory = inventory;
    }

    public RentalAgreement getRentalAgreement() {
        return rentalAgreement;
    }

    public void setRentalAgreement(RentalAgreement rentalAgreement) {
        this.rentalAgreement = rentalAgreement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void createRentalAgreement(RentalAgreement rentalAgreement) {
        this.rentalAgreement = rentalAgreement;
    }

    public void deleteRentalAgreement() {
        this.rentalAgreement = null;
    }

}
