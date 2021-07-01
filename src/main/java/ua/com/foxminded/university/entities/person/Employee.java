package ua.com.foxminded.university.entities.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Employee extends Person {
    @NonNull
    private Integer positionId;
    @NonNull
    private Integer salary;
}
