package ch.heigvd.amt.project.model;

public class Farmer {

    //this three is just for testing the sisk database
    private long id;
    private String firstName;
    private String lastName;
    private String address;

    public Farmer(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }
}

