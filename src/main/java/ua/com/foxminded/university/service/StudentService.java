package ua.com.foxminded.university.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.StudentDAO;
import ua.com.foxminded.university.entities.person.Student;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentDAO studentDAO;

    public Student save(Student student) {
        log.debug("save('{}')", student);
        Student result = studentDAO.save(student);
        log.debug("save('{}') returned '{}'", student, result);
        return result;
    }

    public Student findById(Integer id) {
        log.debug("findById('{}') called", id);
        Student result = studentDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Student not found by id = " + id));
        log.debug("findById('{}') returned '{}'", id, result);
        return result;
    }

    public boolean existsById(Integer id) {
        log.debug("existsById('{}') called", id);
        boolean result = studentDAO.existsById(id);
        log.debug("existsById('{}') returned '{}'", id, result);
        return result;
    }

    public List<Student> findAll() {
        log.debug("findAll() called");
        List<Student> result = studentDAO.findAll();
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    public long count() {
        log.debug("count() called");
        long result = studentDAO.count();
        log.debug("count() returned '{}'", result);
        return result;
    }

    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        studentDAO.deleteById(id);
        log.debug("deleteById('{}') was success", id);
    }

    public void delete(Student student) {
        log.debug("delete('{}') called", student);
        studentDAO.delete(student);
        log.debug("delete('{}') was success", student);
    }

    public void deleteAll() {
        log.debug("deleteAll() called");
        studentDAO.deleteAll();
        log.debug("deleteAll() was success");
    }

    public void update(Student student) {
        log.info("update({}) called", student);
        studentDAO.update(student);
        log.info("update({}) was success", student);
    }
}
