package uwe.tae.sys.model;

public class RentalAgreement {
    private static int nextLeaseNumber = 1000;
    private final int LEASENUMBER;
    private Student student;

    public RentalAgreement(Student student) {
        this.student = student;
        this.LEASENUMBER = getNextLeaseNumber();
    }

    private static synchronized int getNextLeaseNumber() {
        return nextLeaseNumber++;
    }

    public String getLeaseNumber() {
	String LeaseNumber = Integer.toString(LEASENUMBER);
        return LeaseNumber;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
