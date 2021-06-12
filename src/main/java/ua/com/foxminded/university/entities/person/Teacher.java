package ua.com.foxminded.university.entities.person;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ua.com.foxminded.university.entities.Subject;

@Data
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Teacher extends Employee {
    private Subject subject;
}
