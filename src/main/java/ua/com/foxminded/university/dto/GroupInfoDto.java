package ua.com.foxminded.university.dto;

import lombok.*;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Faculty;
import ua.com.foxminded.university.entities.person.Student;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class GroupInfoDto {
    @NonNull
    private Integer id;
    @NonNull
    private Integer numberGroup;
    @NonNull
    private List<Student> students;
    @NonNull
    private Course course;
    @NonNull
    private Faculty faculty;
}
