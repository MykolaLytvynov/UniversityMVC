package ua.com.foxminded.university.entities;

import lombok.*;
import ua.com.foxminded.university.entities.person.Teacher;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Subject {
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    private Integer teacherId;
    @NonNull
    private Integer amountLessons;
}
