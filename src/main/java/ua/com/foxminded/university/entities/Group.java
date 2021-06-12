package ua.com.foxminded.university.entities;

import ua.com.foxminded.university.entities.person.Student;

import java.util.ArrayList;
import java.util.List;
import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter @Setter
@EqualsAndHashCode

public class Group {
    private int id;
    @NonNull
    private int nummerGroup;
    private List<Student> students = new ArrayList<>();

}
