package ua.com.foxminded.university.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.LessonDAO;
import ua.com.foxminded.university.dto.LessonDto;
import ua.com.foxminded.university.entities.Lesson;
import ua.com.foxminded.university.exception.NotFoundException;
import ua.com.foxminded.university.formatter.LessonDtoFormatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonDAO lessonDAO;
    private final LessonDtoFormatter lessonDtoFormatter = new LessonDtoFormatter();

    public Lesson save(Lesson lesson) {
        log.debug("save('{}') called", lesson);
        Lesson result = lessonDAO.save(lesson);
        lesson.getGroups().stream()
                .forEach(group -> lessonDAO.addGroupsToLesson(group.getId(), lesson.getId()));
        log.debug("save('{}') returned '{}'", lesson, result);
        return result;
    }

    public LessonDto findLessonDtoById(Integer id) {
        log.debug("findById('{}') called", id);
        NotFoundException notFoundException = new NotFoundException("Lesson not found by id = " + id);
        if (!lessonDAO.existsById(id)) throw notFoundException;
        Lesson result = lessonDAO.findById(id).get();
        LessonDto lessonDto = new LessonDto();
        lessonDto = lessonDtoFormatter.getLessonDtoByLesson(result);
        log.debug("findById('{}') returned '{}'", id, lessonDto);
        return lessonDto;
    }

    public boolean existsById(Integer id) {
        log.debug("existsById('{}') called", id);
        boolean result = lessonDAO.existsById(id);
        log.debug("existsById('{}') returned '{}'", id, result);
        return result;
    }

    public List<LessonDto> findAll() {
        log.debug("findAll() called");
        List<Lesson> result = lessonDAO.findAll();
        List<LessonDto> lessonsDto = new ArrayList<>();
        result.stream().forEach(lesson -> lessonsDto.add(lessonDtoFormatter.getLessonDtoByLesson(lesson)));
        log.debug("findAll() returned '{}'", lessonsDto);
        return lessonsDto;
    }

    public long count() {
        log.debug("count() called");
        long result = lessonDAO.count();
        log.debug("count() returned '{}'", result);
        return result;
    }

    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        lessonDAO.deleteById(id);
        log.debug("deleteById('{}') was success", id);
    }

    public void delete(Lesson lesson) {
        log.debug("delete('{}') called", lesson);
        lessonDAO.delete(lesson);
        log.debug("delete('{}') was success", lesson);
    }

    public void deleteAll() {
        log.debug("deleteAll() called");
        lessonDAO.deleteAll();
        log.debug("deleteAll() was success");
    }

    public void addGroupsToLesson(Integer groupId, Integer lessonId) {
        log.debug("addGroupsToLesson('{}','{}') called", groupId, lessonId);
        lessonDAO.addGroupsToLesson(groupId, lessonId);
        log.debug("addGroupsToLesson('{}','{}') was success", groupId, lessonId);
    }

    public void deleteGroupsFromLesson(Integer lessonId) {
        log.debug("deleteGroupsFromLesson('{}') called", lessonId);
        lessonDAO.deleteGroupsFromLesson(lessonId);
        log.debug("deleteGroupsFromLesson('{}') was success", lessonId);
    }

    public List<LessonDto> getLessonsBetweenDatesForGroup(LocalDateTime start, LocalDateTime end, Integer idGroup) {
        log.debug("getLessonsBetweenDatesForGroup('{}', '{}', '{}') called", start, end, idGroup);
        List<Lesson> result = lessonDAO.getLessonsBetweenDatesForGroup(start, end, idGroup);
        List<LessonDto> lessonsDto = new ArrayList<>();
        result.stream().forEach(lesson -> lessonsDto.add(lessonDtoFormatter.getLessonDtoByLesson(lesson)));
        log.debug("getLessonsBetweenDatesForGroup('{}', '{}', '{}') returned '{}'", start, end, idGroup, lessonsDto);
        return lessonsDto;
    }

    public List<LessonDto> getLessonsBetweenDatesForTeacher(LocalDateTime start, LocalDateTime end, Integer idTeacher) {
        log.debug("getLessonsBetweenDatesForTeacher('{}', '{}', '{}') called", start, end, idTeacher);
        List<Lesson> result = lessonDAO.getLessonsBetweenDatesForTeacher(start, end, idTeacher);
        List<LessonDto> lessonsDto = new ArrayList<>();
        result.stream().forEach(lesson -> lessonsDto.add(lessonDtoFormatter.getLessonDtoByLesson(lesson)));
        log.debug("getLessonsBetweenDatesForTeacher('{}', '{}', '{}') returned '{}'", start, end, idTeacher, lessonsDto);
        return lessonsDto;
    }

    public void update(Lesson lesson, List<Integer> lessonForGroups) {
        log.debug("update('{}') called", lesson);
        lessonDAO.update(lesson);
        if (!lessonForGroups.isEmpty()) {
            lessonDAO.deleteGroupsFromLesson(lesson.getId());
            for (Integer groupId : lessonForGroups) {
                lessonDAO.addGroupsToLesson(groupId, lesson.getId());
            }
        }
        log.debug("update('{}') was success", lesson);
    }

}
