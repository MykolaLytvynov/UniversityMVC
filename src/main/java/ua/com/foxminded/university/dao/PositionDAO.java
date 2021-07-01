package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.stream.Collectors;

@Component
public class PositionDAO implements CrudOperations<Position, Integer>{
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PositionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static final String SAVE_POSITION = "INSERT INTO positions (name) VALUES (?)";
    public static final String FIND_BY_ID = "SELECT * FROM positions WHERE id = ?";
    public static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM positions WHERE id = ?";
    public static final String FIND_ALL = "SELECT * FROM positions";
    public static final String COUNT = "SELECT COUNT(*) FROM positions";
    public static final String DELETE_POSITION = "DELETE FROM positions WHERE id = ?";
    public static final String DELETE_ALL = "DELETE FROM positions";


    @Override
    public Position save(Position position) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SAVE_POSITION, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, position.getName());
                return ps;
            }
        }, keyHolder);

        position.setId((int)keyHolder.getKeys().get("id"));
        return position;
    }

    @Override
    public Position findById(Integer id) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, new PositionMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean existsById(Integer id) {
        int count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        return count > 0;
    }

    @Override
    public List<Position> findAll() {
        return jdbcTemplate.query(FIND_ALL, new PositionMapper());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update(DELETE_POSITION, id);
    }

    @Override
    public void delete(Position position) {
        jdbcTemplate.update(DELETE_POSITION, position.getId());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }
}
