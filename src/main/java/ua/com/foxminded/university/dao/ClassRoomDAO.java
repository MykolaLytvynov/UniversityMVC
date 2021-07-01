package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mapper.ClassRoomMapper;
import ua.com.foxminded.university.entities.ClassRoom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClassRoomDAO implements CrudOperations<ClassRoom, Integer> {
    private JdbcTemplate jdbcTemplate;

    private static final String SAVE_CLASS_ROOM = "INSERT INTO classRoom (name, description) VALUES (?, ?)";
    private static final String FIND_ALL = "SELECT * FROM classRoom";
    private static final String FIND_BY_ID = "SELECT * FROM classRoom WHERE id = ?";
    private static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM classRoom WHERE id = ?";
    private static final String COUNT = "SELECT COUNT(id) FROM classRoom";
    private static final String DELETE_CLASS_ROOM = "DELETE FROM classRoom WHERE id = ?";
    private static final String DELETE_ALL = "DELETE FROM classRoom";


    @Autowired
    public ClassRoomDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public ClassRoom save(ClassRoom classRoom) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SAVE_CLASS_ROOM, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, classRoom.getName());
                ps.setString(2, classRoom.getDescription());
                return ps;
            }
        }, keyHolder);

        classRoom.setId((int) keyHolder.getKeys().get("id"));
        return classRoom;
    }

    @Override
    public ClassRoom findById(Integer id) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, new ClassRoomMapper())
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public boolean existsById(Integer id) {
        Integer count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        return count > 0;
    }

    @Override
    public List<ClassRoom> findAll() {
        return jdbcTemplate.query(FIND_ALL, new ClassRoomMapper());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update(DELETE_CLASS_ROOM, id);
    }

    @Override
    public void delete(ClassRoom classRoom) {
        jdbcTemplate.update(DELETE_CLASS_ROOM, classRoom.getId());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }
}
