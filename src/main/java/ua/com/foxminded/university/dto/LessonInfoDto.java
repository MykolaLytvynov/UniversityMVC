package ua.com.foxminded.university.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class LessonInfoDto {
    @NonNull
    private Integer lessonId;
    @NonNull
    private LocalDateTime dateTime;
    @NonNull
    private Integer duration;
    @NonNull
    private Integer classRoomId;
    @NonNull
    private Integer subjectId;
    private List<GroupInfoDto> groupsDto;
    private String classRoomName;
    private String subjectName;
}
