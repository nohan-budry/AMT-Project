package ch.heigvd.amt.project.model;

import lombok.*;

@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter
public class Field {
    private int idField;
    private int size;
}
