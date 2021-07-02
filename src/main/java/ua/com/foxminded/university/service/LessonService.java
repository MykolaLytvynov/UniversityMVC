package ua.com.foxminded.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.LessonDAO;
import ua.com.foxminded.university.dao.mapper.LessonMapper;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Lesson;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class LessonService {
    @Autowired
    private LessonDAO lessonDAO;

    public Lesson save(Lesson lesson) {
        return lessonDAO.save(lesson);
    }

    public Lesson findById(Integer id) {
        return Optional.ofNullable(lessonDAO.findById(id)).orElseThrow(() -> new NotFoundException("Lesson not found by id = " + id));
    }

    public boolean existsById(Integer id) {
        return lessonDAO.existsById(id);
    }

    public List<Lesson> findAll() {
        return lessonDAO.findAll();
    }

    public long count() {
        return lessonDAO.count();
    }

    public void deleteById(Integer id) {
        lessonDAO.deleteById(id);
    }

    public void delete(Lesson lesson) {
        lessonDAO.delete(lesson);
    }

    public void deleteAll() {
        lessonDAO.deleteAll();
    }

    public void addGroupsToLesson(Group group, Lesson lesson) {
        lessonDAO.addGroupsToLesson(group, lesson);
    }

    public List<Integer> getAllGroupsOneLesson(Lesson lesson) {
        return lessonDAO.getAllGroupsOneLesson(lesson);
    }

    public void deleteGroupFromLesson(Group group, Lesson lesson) {
        lessonDAO.deleteGroupFromLesson(group, lesson);
    }
}
