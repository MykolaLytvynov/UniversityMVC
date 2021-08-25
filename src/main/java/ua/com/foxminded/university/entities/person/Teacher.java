package ua.com.foxminded.university.entities.person;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ua.com.foxminded.university.entities.Subject;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Teacher extends Employee {
    private List<Subject> subjectList;
}
