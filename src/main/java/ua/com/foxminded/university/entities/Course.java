package ua.com.foxminded.university.entities;

import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Course {
    private Integer id;
    @NonNull
    private Integer numberCourse;
    @NonNull
    private Integer facultyId;
    private List<Group> groups = new ArrayList<>();

}
