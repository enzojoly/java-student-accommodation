package uwe.tae.sys.model;

public class UWE_Accommodation_System {
    private StudentVillage studentVillage;

    private UWE_Accommodation_System() {
	this.studentVillage = StudentVillage.createSVHalls();
    }

    public static UWE_Accommodation_System createSystem() {
        return new UWE_Accommodation_System();
    }

    public StudentVillage getStudentVillage() {
        return studentVillage;
    }
}
