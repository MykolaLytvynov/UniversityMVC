package ua.com.foxminded.university.entities;

import lombok.*;
import ua.com.foxminded.university.entities.person.Student;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Group {
    private Integer id;
    @NonNull
    private Integer numberGroup;
    @NonNull
    private Integer courseId;
    private List<Student> students = new ArrayList<>();


}
