package ua.com.foxminded.university.dto;

import lombok.*;
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
    private Integer courseId;
    @NonNull
    private Integer facultyId;
    private List<Student> students;
    private Integer courseNumber;
    private String facultyName;
}
