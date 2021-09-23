package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mapper.ClassRoomMapper;
import ua.com.foxminded.university.entities.ClassRoom;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClassRoomDAO implements CrudOperations<ClassRoom, Integer> {
    private final JdbcTemplate jdbcTemplate;
    private final ClassRoomMapper classRoomMapper;

    private static final String SAVE_CLASS_ROOM = "INSERT INTO classRoom (name, description) VALUES (?, ?)";
    private static final String FIND_ALL = "SELECT * FROM classRoom";
    private static final String FIND_BY_ID = "SELECT * FROM classRoom WHERE id = ?";
    private static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM classRoom WHERE id = ?";
    private static final String COUNT = "SELECT COUNT(id) FROM classRoom";
    private static final String DELETE_CLASS_ROOM = "DELETE FROM classRoom WHERE id = ?";
    private static final String DELETE_ALL = "DELETE FROM classRoom";

    private static final String UPDATE = "UPDATE classRoom SET name = ?, description = ? WHERE id = ?";


    @Override
    public ClassRoom save(ClassRoom classRoom) {
        log.debug("save('{}') called", classRoom);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SAVE_CLASS_ROOM, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, classRoom.getName());
            ps.setString(2, classRoom.getDescription());
            return ps;
        }, keyHolder);

        Integer id = ofNullable(keyHolder.getKeys())
                .map(map -> (Integer) map.get("id"))
                .orElseThrow(() -> new RuntimeException(format("Query '%s' didn't returned id!", SAVE_CLASS_ROOM)));
        classRoom.setId(id);

        log.debug("save(Classroom) was success. Returned '{}'", classRoom);
        return classRoom;
    }

    @Override
    public Optional<ClassRoom> findById(Integer id) {
        log.debug("findById('{}') called", id);
        ClassRoom result = jdbcTemplate.queryForObject(FIND_BY_ID, classRoomMapper, id);
        log.debug("findById('{}') returned '{}'", id, result);
        return ofNullable(result);
    }

    @Override
    public boolean existsById(Integer id) {
        log.debug("existsById('{}') called", id);
        Integer count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        boolean result = count != null && count > 0;
        log.debug("existsById('{}') returned '{}'", id, result);
        return result;
    }

    @Override
    public List<ClassRoom> findAll() {
        log.debug("findAll() called");
        List<ClassRoom> result = jdbcTemplate.query(FIND_ALL, classRoomMapper);
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    @Override
    public long count() {
        log.debug("count() called");
        Integer result = jdbcTemplate.queryForObject(COUNT, Integer.class);
        log.debug("count() returned '{}'", result);
        return ofNullable(result)
                .orElseThrow(() -> new RuntimeException(format("Query '%s' returned null", COUNT)));
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        jdbcTemplate.update(DELETE_CLASS_ROOM, id);
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(ClassRoom classRoom) {
        log.debug("delete('{}') called", classRoom);
        jdbcTemplate.update(DELETE_CLASS_ROOM, classRoom.getId());
        log.debug("delete('{}') was success", classRoom);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        jdbcTemplate.update(DELETE_ALL);
        log.debug("deleteAll() was success");
    }

    @Override
    public void update(ClassRoom classRoom) {
        log.debug("update('{}') called", classRoom);
        jdbcTemplate.update(UPDATE, classRoom.getName(), classRoom.getDescription(), classRoom.getId());
        log.debug("update('{}') was success", classRoom);
    }
}
