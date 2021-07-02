package ua.com.foxminded.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.GroupDAO;
import ua.com.foxminded.university.dao.mapper.GroupMapper;
import ua.com.foxminded.university.dao.mapper.StudentMapper;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.person.Student;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GroupService {

    @Autowired
    private GroupDAO groupDAO;

    public Group save(Group group) {
        return groupDAO.save(group);
    }

    public Group findById(Integer id) {
        return Optional.ofNullable(groupDAO.findById(id)).orElseThrow(() -> new NotFoundException("Group not found by id = " + id));
    }

    public boolean existsById(Integer id) {
        return groupDAO.existsById(id);
    }

    public List<Group> findAll() {
        return groupDAO.findAll();
    }

    public long count() {
        return groupDAO.count();
    }

    public void deleteById(Integer id) {
        groupDAO.deleteById(id);
    }

    public void delete(Group group) {
        groupDAO.delete(group);
    }

    public void deleteAll() {
        groupDAO.deleteAll();
    }

    public void addGroupToCourse(Course course, Group group) {
        groupDAO.addGroupToCourse(course, group);
    }

    public List<Student> getStudentsOneGroup(Integer id) {
        return groupDAO.getStudentsOneGroup(id);
    }
}
