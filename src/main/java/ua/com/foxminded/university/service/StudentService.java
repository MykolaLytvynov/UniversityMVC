package ua.com.foxminded.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.dao.mapper.StudentMapper;
import ua.com.foxminded.university.entities.person.Student;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class StudentService {
    @Autowired
    private StudentDAO studentDAO;

    public Student save(Student student) {
        return studentDAO.save(student);
    }

    public Student findById(Integer id) {
        return studentDAO.findById(id).orElseThrow(() -> new NotFoundException("Student not found by id = " + id));
    }

    public boolean existsById(Integer id) {
        return studentDAO.existsById(id);
    }

    public List<Student> findAll() {
        return studentDAO.findAll();
    }

    public long count() {
        return studentDAO.count();
    }

    public void deleteById(Integer id) {
        studentDAO.deleteById(id);
    }

    public void delete(Student student) {
        studentDAO.delete(student);
    }

    public void deleteAll() {
        studentDAO.deleteAll();
    }
}
