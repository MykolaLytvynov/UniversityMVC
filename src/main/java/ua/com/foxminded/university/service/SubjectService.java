package ua.com.foxminded.university.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.SubjectDAO;
import ua.com.foxminded.university.dao.mapper.SubjectMapper;
import ua.com.foxminded.university.entities.Subject;
import ua.com.foxminded.university.entities.person.Employee;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectDAO subjectDAO;

    public Subject save(Subject subject) {
        log.debug("save('{}') called", subject);
        Subject result = subjectDAO.save(subject);
        log.debug("save('{}') returned '{}'", subject, result);
        return result;
    }

    public Subject findById(Integer id) {
        log.debug("findById('{}') called", id);
        Subject result = subjectDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Subject not found by id = " + id));
        log.debug("findById('{}') returned '{}'", id, result);
        return result;
    }

    public boolean existsById(Integer id) {
        log.debug("existsById('{}') called", id);
        boolean result = subjectDAO.existsById(id);
        log.debug("existsById('{}') returned '{}'", id, result);
        return result;
    }

    public List<Subject> findAll() {
        log.debug("findAll() called");
        List<Subject> result = subjectDAO.findAll();
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    public long count() {
        log.debug("count() called");
        long result = subjectDAO.count();
        log.debug("count() returned '{}'", result);
        return result;
    }

    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        subjectDAO.deleteById(id);
        log.debug("deleteById('{}') was success", id);
    }

    public void delete(Subject subject) {
        log.debug("delete('{}') called", subject);
        subjectDAO.delete(subject);
        log.debug("delete('{}') was success", subject);
    }

    public void deleteAll() {
        log.debug("deleteAll() called");
        subjectDAO.deleteAll();
        log.debug("deleteAll() was success");
    }


    public void addSubjectToTeacher(Integer subjectId, Integer TeacherId) {
        log.debug("addSubjecctToTeacher('{}', '{}') called", subjectId, TeacherId);
        subjectDAO.addSubjectToTeacher(subjectId, TeacherId);
        log.debug("addSubjecctToTeacher('{}', '{}') was success", subjectId, TeacherId);
    }

    public List<Subject> getAllSubjectsOneTeacher(Integer teacherId) {
        log.debug("getAllSubjectsOneTeacher('{}') called", teacherId);
        List<Subject> result = subjectDAO.getAllSubjectsOneTeacher(teacherId);
        log.debug("getAllSubjectsOneTeacher('{}') returned '{}'", teacherId, result);
        return result;
    }

    public void update(Subject subject) {
        log.debug("update('{}') called", subject);
        subjectDAO.update(subject);
        log.debug("update('{}') was success", subject);
    }

    public List<Subject> getSubjectsWithoutTeacher() {
        log.debug("getSubjectsWithoutTeacher() called");
        List<Subject> result = subjectDAO.getSubjectsWithoutTeacher();
        log.debug("getSubjectsWithoutTeacher() returned '{}', result");
        return result;
    }
}
