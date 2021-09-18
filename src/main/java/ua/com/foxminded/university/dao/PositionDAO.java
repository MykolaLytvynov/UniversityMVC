package ua.com.foxminded.university.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mapper.PositionMapper;
import ua.com.foxminded.university.entities.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Component
@Slf4j
@RequiredArgsConstructor
public class PositionDAO implements CrudOperations<Position, Integer> {
    private final JdbcTemplate jdbcTemplate;
    private final PositionMapper positionMapper;


    private static final String SAVE_POSITION = "INSERT INTO positions (name) VALUES (?)";
    private static final String FIND_BY_ID = "SELECT * FROM positions WHERE id = ?";
    private static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM positions WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM positions";
    private static final String COUNT = "SELECT COUNT(*) FROM positions";
    private static final String DELETE_POSITION = "DELETE FROM positions WHERE id = ?";
    private static final String DELETE_ALL = "DELETE FROM positions";

    private static final String UPDATE = "UPDATE positions SET name = ? WHERE id = ?";

    @Override
    public Position save(Position position) {
        log.debug("save('{}') called", position);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SAVE_POSITION, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, position.getName());
                return ps;
            }
        }, keyHolder);

        Integer id = ofNullable(keyHolder.getKeys())
                .map(map -> (Integer) map.get("id"))
                .orElseThrow(() -> new RuntimeException(format("Query '%s' didn't returned it!", SAVE_POSITION)));
        position.setId(id);
        log.debug("save(Position) was success. Returned '{}'", position);
        return position;
    }

    @Override
    public Optional<Position> findById(Integer id) {
        log.debug("findById('{}') called", id);
        Position result = jdbcTemplate.queryForObject(FIND_BY_ID, positionMapper, id);
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
    public List<Position> findAll() {
        log.debug("findAll() called");
        List<Position> result = jdbcTemplate.query(FIND_ALL, positionMapper);
        log.debug("findAll() returned '{}'", result);
        return result;
    }

    @Override
    public long count() {
        log.debug("count() called");
        long result = jdbcTemplate.queryForObject(COUNT, Integer.class);
        log.debug("count() returned '{}'", result);
        return result;
    }

    @Override
    public void deleteById(Integer id) {
        log.debug("deleteById('{}') called", id);
        jdbcTemplate.update(DELETE_POSITION, id);
        log.debug("deleteById('{}') was success", id);
    }

    @Override
    public void delete(Position position) {
        log.debug("delete('{}') called", position);
        jdbcTemplate.update(DELETE_POSITION, position.getId());
        log.debug("delete('{}') was success", position);
    }

    @Override
    public void deleteAll() {
        log.debug("deleteAll() called");
        jdbcTemplate.update(DELETE_ALL);
        log.debug("deleteAll() was success");
    }

    @Override
    public void update(Position position) {
        log.debug("update('{}') called", position);
        jdbcTemplate.update(UPDATE, position.getName(), position.getId());
        log.debug("update('{}') was success", position);
    }
}
