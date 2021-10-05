package ua.com.foxminded.university.entities;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Subject {
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private Integer amountLessons;
    private Integer teacherId;
    private String teacherName;
    private String teacherLastName;
}
