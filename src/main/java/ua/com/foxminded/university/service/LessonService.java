package ua.com.foxminded.university.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.LessonDAO;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Lesson;
import ua.com.foxminded.university.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonDAO lessonDAO;

    public Lesson save(Lesson lesson) {
        log.debug("save('{}') called", lesson);
        Lesson result = lessonDAO.save(lesson);
        log.debug("save('{}') returned '{}'", lesson, result);
        return result;
    }

    public Lesson findById(Integer id) {
        log.debug("findById('{}') called", id);
        Lesson result = lessonDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Lesson not found by id = " + id));
        log.debug("findById('{}') returned '{}'", id, result);
        return result;
    }

    public boolean existsById(Integer id) {
        log.debug("existsById('{}') called", id);
        boolean result = lessonDAO.existsById(id);
        log.debug("existsById('{}') returned '{}'", id, result);
        return result;
    }

    public List<Lesson> findAll() {
        log.debug("findAll() called");
        List<Lesson> result = lessonDAO.findAll();
        log.debug("findAll() returned '{}'", result);
        return result;
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

    public List<Integer> getAllGroupsOneLesson(Integer lessonId) {
        log.debug("getAllGroupsOneLesson('{}') called", lessonId);
        List<Integer> result = lessonDAO.getAllGroupsOneLesson(lessonId);
        log.debug("getAllGroupsOneLesson('{}') returned '{}'", lessonId, result);
        return result;
    }

    public void deleteGroupFromLesson(Group group, Lesson lesson) {
        log.debug("deleteGroupFromLesson('{}','{}') called", group, lesson);
        lessonDAO.deleteGroupFromLesson(group, lesson);
        log.debug("deleteGroupFromLesson('{}','{}') was success", group, lesson);
    }

    public List<Lesson> getLessonsBetweenData (LocalDateTime start, LocalDateTime end) {
        log.debug("getLessonsBetweenData('{}', '{}') called", start, end);
        List<Lesson> result = lessonDAO.getLessonsBetweenData(start, end);
        log.debug("getLessonsBetweenData('{}', '{}') returned '{}'", start, end, result);
        return result;
    }
}
