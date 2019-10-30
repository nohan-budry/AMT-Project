package ch.heigvd.amt.project.model;

public class ExplotationRight {

    private Farmer farmer;
    private Field field;

    private String issueDate;
    private int duration;
    private double monthlyFee;

    public ExplotationRight(Farmer farmer, Field field) {
        this.farmer = farmer;
        this.field = field;
    }


}
