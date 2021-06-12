package ua.com.foxminded.university.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode

public class Course {
    private int id;
    @NonNull
    private int nummerCourse;
    private List<Group> groups = new ArrayList<>();

}
