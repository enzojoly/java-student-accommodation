package uwe.tae.sys.model;

/**
 *
 * @author enzo
 */

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UWE_Accommodation_System uweSystem;
        uweSystem = UWE_Accommodation_System.createSystem();

        Scanner scanner = new Scanner(System.in);
        while (true) { System.out.println("\nUWE Accommodation System");
            System.out.println("1. Display Halls");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    displayHalls(uweSystem.getStudentVillage().getHalls());
                    break;
                case 2:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void displayHalls(List<Hall> halls) {
        for (Hall hall : halls) {
            System.out.println("\n[HALL NAME: " + hall.getName());
            System.out.println(" Manager: " + hall.getManager().getName());
	    System.out.println(" Manager Tel: " + hall.getManager().getTelephone() + "]\n");
            for (Accommodation accommodation : hall.getAssociatedAccommodations()) {
		System.out.println("\n    ACCOMMODATION "  + accommodation.getID());
                System.out.println(" - Availability: " + accommodation.getAvailabilityStatus());
                System.out.println(" - Cleaning Status: " + accommodation.getCleaningStatus());
                System.out.println(" - Occupancy Status: " + accommodation.getOccupancyStatus());
                System.out.println(" - Type: " + accommodation.getType());
                System.out.println(" - Price: " + accommodation.getPrice());
                System.out.println(" - Inventory: " + String.join(", ", accommodation.getInventory()));
                System.out.println(" - Description: " + accommodation.getDescription());

                RentalAgreement rentalAgreement = accommodation.getRentalAgreement();
                if (rentalAgreement != null) {
		    System.out.println("[LEASE AGREEMENT]");
                    System.out.println(" - Rented By: " + rentalAgreement.getStudent().getName());
		    System.out.println(" - Student Number: " + rentalAgreement.getStudent().getID());
		    System.out.println(" - Student Tel: " + rentalAgreement.getStudent().getTelephone());
                    System.out.println(" - Lease Number: " + rentalAgreement.getLeaseNumber());
                } else {
                    System.out.println("[NO LEASE AGREEMENT]");
		    //print out rental agreement

		    System.out.println(rentalAgreement);
                }
            }
            System.out.println();
        }
    }
}
