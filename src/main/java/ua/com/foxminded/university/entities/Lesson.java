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
    private Integer subjectId;
    @NonNull
    private LocalDateTime dateTime;
    @NonNull
    private Integer duration;
    @NonNull
    private Integer classRoomId;
    private List<Group> lessonForGroups;
}
