package uwe.tae.sys.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class StudentVillage {
    private List<Hall> halls;

    private StudentVillage() {
        this.halls = new ArrayList<>();
    }

    // Static factory method for creating an instance of StudentVillage
    public static StudentVillage createSVHalls() {
        StudentVillage studentVillage = new StudentVillage();
        studentVillage.initialiseHalls();
        return studentVillage;
    }

    private void initialiseHalls() {

	Manager m1 = new Manager("John Smith",	"07345671890", 111111);
        Manager m2 = new Manager("Jane Doe",	"07876543212", 222222);
        Manager m3 = new Manager("Willy Nilly",	"07123849780", 333333);
        Manager m4 = new Manager("Billy Bool", 	"07087654321", 444444);

	halls.add(new Hall("Brecon", 	m1));
	halls.add(new Hall("Cotswold", 	m2));
	halls.add(new Hall("Mendip", 	m3));
	halls.add(new Hall("Quantock", 	m4));

        for (Hall hall : halls) {
            IntStream.rangeClosed(1, 30).forEach(i ->
                hall.addAccommodation(new Accommodation(AccommodationType.STANDARD)));
            IntStream.rangeClosed(31, 40).forEach(i ->
                hall.addAccommodation(new Accommodation(AccommodationType.SUPERIOR)));
        }
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public Hall getHall(String hallName) {
	return halls.stream()
		.filter(hall -> hall.getName().equals(hallName))
		.findFirst()
		.orElse(null);
    }

}
