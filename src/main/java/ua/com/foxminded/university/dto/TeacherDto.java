package ua.com.foxminded.university.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ua.com.foxminded.university.entities.Subject;
import ua.com.foxminded.university.entities.person.Employee;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TeacherDto extends Employee {
    private List<Subject> subjectList;
}
