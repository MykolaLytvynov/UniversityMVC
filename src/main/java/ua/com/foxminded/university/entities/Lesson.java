package ua.com.foxminded.university.entities;

import lombok.Data;
import lombok.NonNull;
import ua.com.foxminded.university.entities.person.Teacher;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Lesson {
    private Integer id;
    @NonNull
    private Subject subject;
    @NonNull
    private Teacher teacher;
    @NonNull
    private LocalDateTime localDateTime;
    @NonNull
    private Integer duration;
    @NonNull
    private ClassRoom classRoom;
    @NonNull
    private List<Group> lessonForGroups;
}
