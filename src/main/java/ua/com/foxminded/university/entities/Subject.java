package ua.com.foxminded.university.entities;

import ua.com.foxminded.university.entities.person.Teacher;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter @Setter
@EqualsAndHashCode

public class Subject {
    private int id;
    private String name;
    @NonNull
    private String description;
    @NonNull
    private Teacher teacher;
    @NonNull
    private int amountLessons;
}
