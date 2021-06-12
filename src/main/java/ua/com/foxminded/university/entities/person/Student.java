package ua.com.foxminded.university.entities.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import ua.com.foxminded.university.entities.Group;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Student extends Person {
    private Group group;
}
