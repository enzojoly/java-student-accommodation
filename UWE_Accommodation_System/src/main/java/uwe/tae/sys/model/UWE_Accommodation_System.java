package uwe.tae.sys.model;

public class UWE_Accommodation_System {
    private static UWE_Accommodation_System instance;
    private StudentVillage studentVillage;

    public static synchronized UWE_Accommodation_System getSystem() {
	    if (instance == null) {
	        instance = new UWE_Accommodation_System();
	    }
	    return instance;
    }

    private UWE_Accommodation_System() {
	this.studentVillage = StudentVillage.createSVHalls();
    }

    public StudentVillage getStudentVillage() {
        return studentVillage;
    }

}
