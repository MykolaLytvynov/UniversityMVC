package ua.com.foxminded.university.dto;

import lombok.*;
import ua.com.foxminded.university.entities.Group;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class CourseInfoDto {
    @NonNull
    private Integer id;
    @NonNull
    private Integer numberCourse;
    @NonNull
    private Integer facultyId;
    private List<Group> groups;
    private String facultyName;
}
