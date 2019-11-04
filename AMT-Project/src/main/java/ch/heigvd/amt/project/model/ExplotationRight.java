package ch.heigvd.amt.project.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter
public class ExplotationRight {

    private Farmer farmer;
    private Field field;
    private String issueDate;
    private int duration;
    private double monthlyFee;




}
