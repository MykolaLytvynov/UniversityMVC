package ua.com.foxminded.university.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.CourseDAO;
import ua.com.foxminded.university.dao.mapper.GroupMapper;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Faculty;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseDAO courseDAO;

    public Course save(Course course) {
        log.debug("save('{}') called", course);
        Course result = courseDAO.save(course);
        log.debug("save('{}') returned '{}'", course, result);
        return result;
    }

    public Course findById(Integer id) {
        log.debug("findById('{}') called", id);
        Course result = courseDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Course not found by id = " + id));
        log.debug("findById('{}') returned '{}'", id, result);
        return result;
    }

    public boolean existsById(Integer id) {
        log.debug("existsById('{}') called", id);
        boolean result = courseDAO.existsById(id);
        log.debug("existsById('{}') returned '{}'", id, result);
        return result;
    }

    public List<Course> findAll() {
        log.debug("findAll() called");
        List<Course> result = courseDAO.findAll();
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    public long count() {
        log.debug("count() called");
        long result = courseDAO.count();
        log.debug("count() returned '{}'", result);
        return result;
    }

    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        courseDAO.deleteById(id);
        log.debug("deleteById('{}') was success", id);
    }

    public void delete(Course course) {
        log.debug("delete('{}') called", course);
        courseDAO.delete(course);
        log.debug("delete('{}') was success", course);
    }

    public void deleteAll() {
        log.debug("deleteAll() called");
        courseDAO.deleteAll();
        log.debug("deleteAll() was success");
    }

    public List<Group> getGroupsOneCourse(Integer courseId) {
        log.debug("getGroupsOneCourse('{}') called", courseId);
        List<Group> result = courseDAO.getGroupsOneCourse(courseId);
        log.debug("getGroupsOneCourse('{}') returned '{}'", courseId, result);
        return result;
    }
}