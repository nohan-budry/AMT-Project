package ch.heigvd.amt.project.model;


import lombok.*;

@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter
public class Farmer {

    private String username;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String password;


}

