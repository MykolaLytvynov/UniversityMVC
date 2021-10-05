package ua.com.foxminded.university.dto;

import lombok.*;
import ua.com.foxminded.university.entities.Group;

import java.util.List;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class LessonDto {
    private Integer id;
    @NonNull
    private String dateTime;
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
