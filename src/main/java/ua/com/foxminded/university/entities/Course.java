package ua.com.foxminded.university.entities;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Course {
    private Integer id;
    @NonNull
    private Integer numberCourse;
    @NonNull
    private Integer facultyId;
    private List<Group> groups;
    private String facultyName;
}
