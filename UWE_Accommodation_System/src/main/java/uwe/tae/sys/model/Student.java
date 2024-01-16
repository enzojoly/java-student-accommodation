package uwe.tae.sys.model;

public class Student extends Person {
    private int studentIDNumber;
    private int leaseNumber;

    public Student(String fullName, String telephone, int studentIDNumber) {
        super(fullName, telephone);
        this.studentIDNumber = studentIDNumber;
    }

    public String getID() {
	String ID = "S" + studentIDNumber;
	return ID;
    }

    public void setID(int studentIDNumber) {
        this.studentIDNumber = studentIDNumber;
    }

    public int getLeaseNumber() {
        return leaseNumber;
    }

    public void setLeaseNumber(int leaseNumber) {
        this.leaseNumber = leaseNumber;
    }
}
