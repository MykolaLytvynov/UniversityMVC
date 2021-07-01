package ua.com.foxminded.university.entities;

import lombok.Data;
import lombok.NonNull;
import ua.com.foxminded.university.entities.person.Teacher;

@Data
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
