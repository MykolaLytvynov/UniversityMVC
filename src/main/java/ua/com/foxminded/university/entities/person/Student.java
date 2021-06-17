package ua.com.foxminded.university.entities.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ua.com.foxminded.university.entities.Group;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Student extends Person {
    private Group group;
}
