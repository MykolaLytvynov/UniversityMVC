package ua.com.foxminded.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.ClassRoomDAO;
import ua.com.foxminded.university.entities.ClassRoom;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class ClassRoomService {
    @Autowired
    private ClassRoomDAO classRoomDAO;

    public ClassRoom save(ClassRoom classRoom) {
        return classRoomDAO.save(classRoom);
    }

    public ClassRoom findById(Integer id) {
        return Optional.ofNullable(classRoomDAO.findById(id)).orElseThrow(() -> new NotFoundException("Class room not found by id = " + id));
    }

    public boolean existsById(Integer id) {
        return classRoomDAO.existsById(id);
    }

    public List<ClassRoom> findAll() {
        return classRoomDAO.findAll();
    }

    public long count() {
        return classRoomDAO.count();
    }

    public void deleteById(Integer id) {
        classRoomDAO.deleteById(id);
    }

    public void delete(ClassRoom classRoom) {
        classRoomDAO.delete(classRoom);
    }

    public void deleteAll() {
        classRoomDAO.deleteAll();
    }
}
