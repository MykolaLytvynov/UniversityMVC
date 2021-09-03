package ua.com.foxminded.university.dto;

import lombok.*;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class SubjectDto {
    @NonNull
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
