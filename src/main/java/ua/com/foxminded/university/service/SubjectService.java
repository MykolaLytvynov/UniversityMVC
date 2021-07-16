package ua.com.foxminded.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.SubjectDAO;
import ua.com.foxminded.university.dao.mapper.SubjectMapper;
import ua.com.foxminded.university.entities.Subject;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class SubjectService {
    @Autowired
    private SubjectDAO subjectDAO;

    public Subject save(Subject subject) {
        return subjectDAO.save(subject);
    }

    public Subject findById(Integer id) {
        return subjectDAO.findById(id).orElseThrow(() -> new NotFoundException("Subject not found by id = " + id));
    }

    public boolean existsById(Integer id) {
        return subjectDAO.existsById(id);
    }

    public List<Subject> findAll() {
        return subjectDAO.findAll();
    }

    public long count() {
        return subjectDAO.count();
    }

    public void deleteById(Integer id) {
        subjectDAO.deleteById(id);
    }

    public void delete(Subject subject) {
        subjectDAO.delete(subject);
    }

    public void deleteAll() {
        subjectDAO.deleteAll();
    }


    public void addSubjecctToTeacher(Subject subject, Employee employee) {
        subjectDAO.addSubjecctToTeacher(subject, employee);
    }

    public List<Subject> getAllSubjectsOneTeacher(Integer teacherId) {
        return subjectDAO.getAllSubjectsOneTeacher(teacherId);
    }
}
