package ua.com.foxminded.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.CourseDAO;
import ua.com.foxminded.university.dao.mapper.GroupMapper;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Faculty;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class CourseService {
    @Autowired
    private CourseDAO courseDAO;

    public Course save(Course course) {
        return courseDAO.save(course);
    }

    public Course findById(Integer id) {
        return courseDAO.findById(id).orElseThrow(() -> new NotFoundException("Course not found by id = " + id));
    }

    public boolean existsById(Integer id) {
        return courseDAO.existsById(id);
    }

    public List<Course> findAll() {
        return courseDAO.findAll();
    }

    public long count() {
        return courseDAO.count();
    }

    public void deleteById(Integer id) {
        courseDAO.deleteById(id);
    }

    public void delete(Course course) {
        courseDAO.delete(course);
    }

    public void deleteAll() {
        courseDAO.deleteAll();
    }

    public void addCourseToFaculty(Course course, Faculty faculty) {
        courseDAO.addCourseToFaculty(course, faculty);
    }

    public List<Group> getGroupsOneCourse(Integer id) {
        return courseDAO.getGroupsOneCourse(id);
    }
}
