package ua.com.foxminded.university.entities;

import ua.com.foxminded.university.entities.person.Teacher;

import java.time.LocalDateTime;
import java.util.List;

import lombok.*;

@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter @Setter
@EqualsAndHashCode

public class Lesson {
    private int id;
    @NonNull
    private Subject subject;
    @NonNull
    private Teacher teacher;
    @NonNull
    private LocalDateTime localDateTime;
    @NonNull
    private int duration;
    @NonNull
    private ClassRoom classRoom;
    @NonNull
    private List<Group> lessonForGroups;
}
