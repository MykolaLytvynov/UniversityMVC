package ua.com.foxminded.university.entities;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Lesson {
    private Integer id;
    @NonNull
    private LocalDateTime dateTime;
    @NonNull
    private Integer duration;
    @NonNull
    private Integer classRoomId;
    @NonNull
    private Integer subjectId;
    private List<Group> groups;
    private String classRoomName;
    private String subjectName;
}
