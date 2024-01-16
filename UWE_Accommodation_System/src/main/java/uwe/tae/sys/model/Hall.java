package uwe.tae.sys.model;

import java.util.ArrayList;
import java.util.List;

public class Hall {
    private String hallName;
    private List<Accommodation> associatedAccommodations;
    private Manager manager;

    public Hall(String hallName, Manager manager) {
        this.hallName = hallName;
        this.manager = manager;
        this.associatedAccommodations = new ArrayList<>();
    }

    public void addAccommodation(Accommodation accommodation) {
        this.associatedAccommodations.add(accommodation);
    }

    public String getName() {
        return hallName;
    }

    public void setName(String hallName) {
        this.hallName = hallName;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<Accommodation> getAssociatedAccommodations() {
        return associatedAccommodations;
    }
}
