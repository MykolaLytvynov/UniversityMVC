package ua.com.foxminded.university.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.com.foxminded.university.dao.PositionDAO;
import ua.com.foxminded.university.entities.Lesson;
import ua.com.foxminded.university.entities.Position;
import ua.com.foxminded.university.exception.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PositionService {

    private final PositionDAO positionDAO;

    public Position save(Position position) {
        log.debug("save('{}') called", position);
        Position result = positionDAO.save(position);
        log.debug("save('{}') returned '{}'", position, result);
        return result;
    }

    public Position findById(Integer id) {
        log.debug("findById('{}') called", id);
        Position result = positionDAO.findById(id)
                .orElseThrow(() -> new NotFoundException("Position not found by id = " + id));
        log.debug("findById('{}') returned '{}'", id, result);
        return result;
    }

    public boolean existsById(Integer id) {
        log.debug("existsById('{}') called", id);
        boolean result = positionDAO.existsById(id);
        log.debug("existsById('{}') returned '{}'", id, result);
        return result;
    }

    public List<Position> findAll() {
        log.debug("findAll() called");
        List<Position> result = positionDAO.findAll();
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    public long count() {
        log.debug("count() called");
        long result = positionDAO.count();
        log.debug("count() returned '{}'", result);
        return result;
    }

    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        positionDAO.deleteById(id);
        log.debug("deleteById('{}') was success", id);
    }

    public void delete(Position position) {
        log.debug("deleteById('{}') called", position);
        positionDAO.delete(position);
        log.debug("deleteById('{}') was success", position);
    }

    public void deleteAll() {
        log.debug("deleteAll() called");
        positionDAO.deleteAll();
        log.debug("deleteAll() was success");
    }

    public void update(Position position) {
        log.debug("update('{}') called", position);
        positionDAO.update(position);
        log.debug("update('{}') was success", position);
    }
}
