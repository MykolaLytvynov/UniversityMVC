package ua.com.foxminded.university.formatter;

import ua.com.foxminded.university.dto.LessonDto;
import ua.com.foxminded.university.entities.Lesson;

import java.time.format.DateTimeFormatter;


public class LessonDtoFormatter {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public LessonDto getLessonDtoByLesson(Lesson lesson) {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setId(lesson.getId());
        lessonDto.setDateTime(lesson.getDateTime().format(formatter));
        lessonDto.setDuration(lesson.getDuration());
        lessonDto.setClassRoomId(lesson.getClassRoomId());
        lessonDto.setSubjectId(lesson.getSubjectId());
        lessonDto.setGroups(lesson.getGroups());
        lessonDto.setClassRoomName(lesson.getClassRoomName());
        lessonDto.setSubjectName(lesson.getSubjectName());
        return lessonDto;
    }
}
