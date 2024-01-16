package uwe.tae.sys.model;

public class Manager extends Person {
    private int employeeID;

    public Manager(String fullName, String telephone, int employeeID) {
        super(fullName, telephone);
        this.employeeID = employeeID;
    }

    public int getID() {
        return employeeID;
    }

    public void setID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setID(String employeeID) {
	this.employeeID = Integer.parseInt(employeeID);
    }
}
