package ua.com.foxminded.university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ua.com.foxminded.university.dao.mapper.GroupMapper;
import ua.com.foxminded.university.dao.mapper.LessonMapper;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Lesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Component
public class LessonDAO implements CrudOperations<Lesson, Integer> {
    private JdbcTemplate jdbcTemplate;

    public static final String SAVE_LESSON = "INSERT INTO lessons (subjectId, dateTime, duration, classRoomId) VALUES (?, ?, ?, ?)";
    public static final String FIND_BY_ID = "SELECT * FROM lessons WHERE id = ?";
    public static final String EXISTS_BY_ID = "SELECT COUNT(*) FROM lessons WHERE id = ?";
    public static final String FIND_ALL = "SELECT * FROM lessons";
    public static final String COUNT = "SELECT COUNT(*) FROM lessons";
    public static final String DELETE_LESSON = "DELETE FROM lessons WHERE id = ?";
    public static final String DELETE_ALL = "DELETE FROM lessons";

    public static final String GET_ALL_GROUPS_ONE_LESSON = "SELECT idGroup FROM groupsLessons WHERE idLesson = ?";
    public static final String ADD_GROUPS_TO_LESSON = "INSERT INTO groupsLessons (idGroup, idLesson) VALUES (?, ?)";
    public static final String DELETE_GROUP_FROM_LESSON = "DELETE FROM groupsLessons WHERE idGroup = ? AND idLesson = ?";

    @Autowired
    public LessonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Lesson save(Lesson lesson) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(SAVE_LESSON, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, lesson.getSubjectId());
                ps.setObject(2, lesson.getDateTime());
                ps.setInt(3, lesson.getDuration());
                ps.setInt(4, lesson.getClassRoomId());
                return ps;
            }
        }, keyHolder);
        lesson.setId((int) keyHolder.getKeys().get("id"));
        return lesson;
    }

    @Override
    public Optional<Lesson> findById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, new LessonMapper())
                .stream()
                .findAny()
                .orElse(null));
    }

    @Override
    public boolean existsById(Integer id) {
        int count = jdbcTemplate.queryForObject(EXISTS_BY_ID, Integer.class, id);
        return count > 0;
    }

    @Override
    public List<Lesson> findAll() {
        return jdbcTemplate.query(FIND_ALL, new LessonMapper());
    }

    @Override
    public long count() {
        return jdbcTemplate.queryForObject(COUNT, Integer.class);
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update(DELETE_LESSON, id);
    }

    @Override
    public void delete(Lesson lesson) {
        jdbcTemplate.update(DELETE_LESSON, lesson.getId());
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL);
    }

    public void addGroupsToLesson(Group group, Lesson lesson) {
        jdbcTemplate.update(ADD_GROUPS_TO_LESSON, group.getId(), lesson.getId());
    }

    public List<Integer> getAllGroupsOneLesson(Lesson lesson) {
        return jdbcTemplate.queryForList(GET_ALL_GROUPS_ONE_LESSON, Integer.class, lesson.getId());
    }

    public void deleteGroupFromLesson (Group group, Lesson lesson) {
        jdbcTemplate.update(DELETE_GROUP_FROM_LESSON, group.getId(), lesson.getId());
    }
}
