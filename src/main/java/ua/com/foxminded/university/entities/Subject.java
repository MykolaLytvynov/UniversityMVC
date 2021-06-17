package ua.com.foxminded.university.entities;

import lombok.Data;
import lombok.NonNull;
import ua.com.foxminded.university.entities.person.Teacher;

@Data
public class Subject {
    private int id;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private Teacher teacher;
    @NonNull
    private int amountLessons;
}
