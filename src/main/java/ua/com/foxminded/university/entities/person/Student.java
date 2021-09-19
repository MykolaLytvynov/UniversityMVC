package ua.com.foxminded.university.entities.person;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ua.com.foxminded.university.entities.person.Person;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Student extends Person {
    @NonNull
    private Integer groupId;
    private Integer numberGroup;
    private Integer numberCourse;
    private String facultyName;
    private Integer facultyId;
    private Integer courseId;
}
