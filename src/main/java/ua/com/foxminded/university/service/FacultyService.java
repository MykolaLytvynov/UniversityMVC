package ua.com.foxminded.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.FacultyDAO;
import ua.com.foxminded.university.dao.mapper.CourseMapper;
import ua.com.foxminded.university.dao.mapper.FacultyMapper;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Faculty;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class FacultyService {

    @Autowired
    private FacultyDAO facultyDAO;

    public Faculty save(Faculty faculty) {
        return facultyDAO.save(faculty);
    }

    public Faculty findById(Integer id) {
        return Optional.ofNullable(facultyDAO.findById(id)).orElseThrow(() -> new NotFoundException("Faculty not found by id = " + id));
    }

    public boolean existsById(Integer id) {
        return facultyDAO.existsById(id);
    }

    public List<Faculty> findAll() {
        return facultyDAO.findAll();
    }

    public long count() {
        return facultyDAO.count();
    }

    public void deleteById(Integer id) {
        facultyDAO.deleteById(id);
    }

    public void delete(Faculty faculty) {
        facultyDAO.delete(faculty);
    }

    public void deleteAll() {
        facultyDAO.deleteAll();
    }

    public List<Course> getCoursesOneFaculty(Integer id) {
        return facultyDAO.getCoursesOneFaculty(id);
    }
}
