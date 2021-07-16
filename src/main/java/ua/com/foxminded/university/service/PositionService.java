package ua.com.foxminded.university.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.PositionDAO;
import ua.com.foxminded.university.dao.mapper.PositionMapper;
import ua.com.foxminded.university.entities.Position;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Component
public class PositionService {
    @Autowired
    private PositionDAO positionDAO;

    public Position save(Position position) {
        return positionDAO.save(position);
    }

    public Position findById(Integer id) {
        return positionDAO.findById(id).orElseThrow(() -> new NotFoundException("Position not found by id = " + id));
    }

    public boolean existsById(Integer id) {
        return positionDAO.existsById(id);
    }

    public List<Position> findAll() {
        return positionDAO.findAll();
    }

    public long count() {
        return positionDAO.count();
    }

    public void deleteById(Integer id) {
        positionDAO.deleteById(id);
    }

    public void delete(Position position) {
        positionDAO.delete(position);
    }

    public void deleteAll() {
        positionDAO.deleteAll();
    }
}
