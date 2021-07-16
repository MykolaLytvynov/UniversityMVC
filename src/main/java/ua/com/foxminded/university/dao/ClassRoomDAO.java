package ua.com.foxminded.university.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Optional;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassRoomDAO.class);

    @Autowired
    public ClassRoomDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public ClassRoom save(ClassRoom classRoom) {
        LOGGER.debug("Saving classroom - {}", classRoom);
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

        if(classRoom.getId()!=0) {
            LOGGER.debug("Successful adding classroom - {}", classRoom);
        } else LOGGER.error("Classroom was not added");

        return classRoom;
    }

    @Override
    public Optional<ClassRoom> findById(Integer id) {
        LOGGER.debug("Find classroom by id - {}", id);
        return Optional.ofNullable(jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, new ClassRoomMapper())
                .stream()
                .findAny()
                .orElse(null));
    }

    @Override
    public boolean existsById(Integer id) {
        LOGGER.debug("checking if such a classroom exists by id - {}", id);
        Integer count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        return count > 0;
    }

    @Override
    public List<ClassRoom> findAll() {
        LOGGER.debug("Getting all classrooms");
        return jdbcTemplate.query(FIND_ALL, new ClassRoomMapper());
    }

    @Override
    public long count() {
        LOGGER.debug("Getting count classrooms");
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }

    @Override
    public void deleteById(Integer id) {
        LOGGER.debug("Deleting classrooms by id - {}", id);
        jdbcTemplate.update(DELETE_CLASS_ROOM, id);
    }

    @Override
    public void delete(ClassRoom classRoom) {
        LOGGER.debug("Deleting classrooms - {}", classRoom);
        jdbcTemplate.update(DELETE_CLASS_ROOM, classRoom.getId());
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("Deleting all classrooms");
        jdbcTemplate.update(DELETE_ALL);
    }
}
