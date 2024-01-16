package uwe.tae.sys.model;

public abstract class Person {
    protected String fullName;
    protected String telephone;

    public Person(String fullName, String telephone) {
        this.fullName = fullName;
        this.telephone = telephone;
    }

    public String getName() {
        return fullName;
    }

    public void setName(String fullName) {
        this.fullName = fullName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
