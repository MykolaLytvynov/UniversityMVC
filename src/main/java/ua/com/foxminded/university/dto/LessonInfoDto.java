package ua.com.foxminded.university.dto;

import lombok.*;
import ua.com.foxminded.university.entities.*;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class LessonInfoDto {
    @NonNull
    private Integer id;
    @NonNull
    private LocalDateTime dateTime;
    @NonNull
    private Integer duration;
    private ClassRoom classRoom;
    private List<Group> groups;
    private Course course;
    private Faculty faculty;
    private Subject subject;
}
