package ch.heigvd.amt.project.model;

import lombok.*;

@Builder(toBuilder = true)
@EqualsAndHashCode
@Getter
public class Field {
    private long idField;
    private int size;
}
