package uwe.tae.sys.model;

/**
 *
 * @author enzo
 */

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UWE_Accommodation_System system = new UWE_Accommodation_System();
        initializeSystem(system);

        Scanner scanner = new Scanner(System.in);
        while (true) { System.out.println("\nUWE Accommodation System");
            System.out.println("1. Display Halls");
            System.out.println("2. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    displayHalls(system.getStudentVillage().getHalls());
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

    private static void initializeSystem(UWE_Accommodation_System system) {
        Manager manager1 = new Manager("John Smith", "1234567890", 1);
        Manager manager2 = new Manager("Jane Doe", "0987654321", 2);

        Hall hall1 = new Hall("Brecon", manager1);
        Hall hall2 = new Hall("Cotswold", manager2);

        Accommodation acc1 = new Accommodation(AccommodationType.STANDARD, 700.00, "Standard Room Description", Arrays.asList("Bed", "Desk"), CleaningStatus.CLEAN);
        Accommodation acc2 = new Accommodation(AccommodationType.SUPERIOR, 750.00, "Superior Room Description", Arrays.asList("Bed", "Desk", "Sofa"), CleaningStatus.OFFLINE);
        Accommodation acc3 = new Accommodation(AccommodationType.STANDARD, 700.00, "Standard Room Description", Arrays.asList("Bed", "Desk"), CleaningStatus.CLEAN);
        Accommodation acc4 = new Accommodation(AccommodationType.SUPERIOR, 750.00, "Superior Room Description", Arrays.asList("Bed", "Desk", "Sofa"), CleaningStatus.DIRTY);

        hall1.addAccommodation(acc1);
        hall1.addAccommodation(acc2);
        hall2.addAccommodation(acc3);
        hall2.addAccommodation(acc4);

        Student student1 = new Student("Alice Johnson", "07576543210", 3001);
        RentalAgreement rentalAgreement1 = new RentalAgreement(student1);
        acc1.setRentalAgreement(rentalAgreement1);

	Student student2 = new Student("Charlie Jones", "07065343210", 3024);
        RentalAgreement rentalAgreement2 = new RentalAgreement(student2);
        acc4.setRentalAgreement(rentalAgreement2);

        system.getStudentVillage().getHalls().add(hall1);
        system.getStudentVillage().getHalls().add(hall2);
    }

    private static void displayHalls(List<Hall> halls) {
        for (Hall hall : halls) {
            System.out.println("\n[HALL NAME: " + hall.getHallName());
            System.out.println(" Manager: " + hall.getManager().getFullName());
	    System.out.println(" Manager Tel: " + hall.getManager().getTelephoneNumber() + "]\n");
            for (Accommodation accommodation : hall.getAssociatedAccommodations()) {
		System.out.println("\n    ACCOMMODATION "  + accommodation.getAccommodationNumber());
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
                    System.out.println(" - Rented By: " + rentalAgreement.getStudent().getFullName());
		    System.out.println(" - Student Number: " + rentalAgreement.getStudent().getStudentIDNumber());
		    System.out.println(" - Student Tel: " + rentalAgreement.getStudent().getTelephoneNumber());
                    System.out.println(" - Lease Number: " + rentalAgreement.getLeaseNumber());
                } else {
                    System.out.println("[NO LEASE AGREEMENT]");
                }
            }
            System.out.println();
        }
    }
}
